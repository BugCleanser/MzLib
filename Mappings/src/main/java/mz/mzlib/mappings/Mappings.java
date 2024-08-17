package mz.mzlib.mappings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Supplier;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Mappings implements IMappings
{
    public Map<String, String> classes = new HashMap<>();
    public Map<String, Map<String, String>> fields = new HashMap<>();
    public Map<String, Map<MappingMethod, String>> methods = new HashMap<>();

    @Override
    public String mapClass(String from)
    {
        return this.classes.getOrDefault(from,from);
    }

    @Override
    public String mapField(String fromClass,String fromField)
    {
        return Optional.ofNullable(this.fields.get(fromClass)).map(it->it.getOrDefault(fromField,fromField)).orElse(fromField);
    }

    @Override
    public String mapMethod(String fromClass, MappingMethod fromMethod)
    {
        return Optional.ofNullable(this.methods.get(fromClass)).map(it->it.getOrDefault(fromMethod,fromMethod.name)).orElse(fromMethod.name);
    }

    public static Mappings parseYarnLegacy(String mappings)
    {
        Mappings result = new Mappings();
        Iterator<String> it = new BufferedReader(new StringReader(mappings)).lines().filter(_it -> !_it.startsWith("#")).iterator();
        it.next(); // v1	official	intermediary	named
        for (String line = it.next(); line != null; line = it.hasNext() ? it.next() : null)
        {
            String[] a = line.split("\t");
            switch (a[0])
            {
                case "CLASS":
                    result.classes.put(a[1].replace('/','.'), a[3].replace('/','.'));
                    break;
                case "FIELD":
                    result.fields.computeIfAbsent(a[1].replace('/','.'), it1 -> new HashMap<>()).put(a[3], a[5]);
                    break;
                case "METHOD":
                    result.methods.computeIfAbsent(a[1].replace('/','.'), it1 -> new HashMap<>()).put(MappingMethod.parse(a[3], a[2]), a[5]);
                    break;
                default:
                    throw new UnsupportedOperationException(line);
            }
        }
        return result;
    }
    public static Mappings parseYarn(ZipInputStream mappings)
    {
        Mappings result = new Mappings();
        try
        {
            for (ZipEntry i = mappings.getNextEntry(); i != null; i = mappings.getNextEntry())
            {
                String[] parts = i.getName().split("/");
                if (!i.isDirectory() && parts.length > 2 && parts[1].equals("mappings") && i.getName().endsWith(".mapping"))
                {
                    Iterator<String> it = new BufferedReader(new StringReader(new String(Util.readInputStream(mappings), StandardCharsets.UTF_8))).lines().map(String::trim).filter(_it -> !_it.startsWith("COMMENT")).iterator();
                    List<String> classPath=new ArrayList<>();
                    Supplier<String> className=()->String.join("$",classPath);
                    while ( it.hasNext() )
                    {
                        String line = it.next();
                        int tab=0;
                        while(tab<line.length()&&line.charAt(tab)=='\t')
                            tab++;
                        String[] a = line.substring(tab).split(" ");
                        while(classPath.size()>tab)
                            classPath.remove(classPath.size()-1);
                        switch (a[0])
                        {
                            case "CLASS":
                                classPath.add(a[1].replace('/','.'));
                                if (a.length == 3)
                                    result.classes.put(className.get(), a[2].replace('/','.'));
                                else
                                    result.classes.put(className.get(), className.get());
                                break;
                            case "FIELD":
                                result.fields.computeIfAbsent(className.get(), it1 -> new HashMap<>()).put(a[1], a[2]);
                                break;
                            case "METHOD":
                                if (a.length >= 4)
                                    result.methods.computeIfAbsent(className.get(), it1 -> new HashMap<>()).put(MappingMethod.parse(a[1], a[3]), a[2]);
                                break;
                            case "ARG":
                                break;
                            default:
                                throw new UnsupportedOperationException(line);
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            throw new AssertionError(e);
        }
        return result;
    }
    public static Mappings parseYarnIntermediary(String mappings)
    {
        Mappings result = new Mappings();
        Iterator<String> it = new BufferedReader(new StringReader(mappings)).lines().filter(_it -> !_it.startsWith("#")).iterator();
        it.next(); // v1	official	intermediary
        while (it.hasNext())
        {
            String line = it.next();
            String[] a = line.split("\t");
            switch (a[0])
            {
                case "CLASS":
                    result.classes.put(a[1].replace('/','.'), a[2].replace('/','.'));
                    break;
                case "FIELD":
                    result.fields.computeIfAbsent(a[1].replace('/','.'), it1 -> new HashMap<>()).put(a[3], a[4]);
                    break;
                case "METHOD":
                    result.methods.computeIfAbsent(a[1].replace('/','.'), it1 -> new HashMap<>()).put(MappingMethod.parse(a[3], a[2]), a[4]);
                    break;
                default:
                    throw new UnsupportedOperationException(line);
            }
        }
        return result;
    }
    public static Mappings parseMojang(String raw)
    {
        Mappings result = new Mappings();
        Iterator<String> lines = new BufferedReader(new StringReader(raw)).lines().filter(l -> !l.isEmpty() && !l.startsWith("#")).iterator();
        String line = lines.hasNext() ? lines.next() : null;
        while (line != null)
        {
            String[] cl = line.substring(0, line.length() - 1).split("->");
            cl[0] = cl[0].trim();

            cl[1] = cl[1].trim();
            result.classes.put(cl[0], cl[1]);
            while (true)
            {
                line = lines.hasNext() ? lines.next() : null;
                if (line == null || !line.startsWith(" ") && !line.startsWith("\t"))
                {
                    break;
                }
                String[] m = line.split("->");
                String from = m[0].trim(), to = m[1].trim();
                if (from.endsWith(")"))
                {
                    String[] a = from.split(" "); // 手动混淆
                    String[] b = a[0].split(":"), c = a[1].split("\\(");
                    result.methods.computeIfAbsent(cl[0], it -> new HashMap<>())
                            .put(new MappingMethod(c[0],
                                    Arrays.stream(c[1].substring(0, c[1].length() - 1)
                                                    .split(","))
                                            .map(Mappings::type2desc)
                                            .toArray(String[]::new)), to);
                }
                else
                {
                    result.fields.computeIfAbsent(cl[0], it -> new HashMap<>()).put(from.split(" ")[1], to);
                }
            }
        }
        return result;
    }
    public static Mappings parseSpigot(SpigotMappings mappings)
    {
        Mappings result = new Mappings();
        new BufferedReader(new StringReader(mappings.classMappings)).lines()
                .filter(l -> !l.startsWith("#"))
                .forEach(line -> {
                    String obfName = line.split(" ")[0];
                    String deobfName = line.split(" ")[1].replace('/', '.');
                    result.classes.put(deobfName,obfName);
                });

        new BufferedReader(new StringReader(mappings.memberMappings)).lines()
                .filter(l -> !l.startsWith("#"))
                .forEach(it1-> handleMember(result,it1));

        return result;
    }
    public static void handleMember(Mappings mappings, String str)
    {
        String[] line = str.split(" ");

        if (line.length == 3)
        {
            String clazz = line[0];
            String obfName = line[1];
            String deobfName = line[2];

            HashMap<String, String> map = new HashMap<>();
            map.put(deobfName,obfName);
            mappings.fields.put(clazz, Collections.unmodifiableMap(map));
        }
        else if (line.length == 4)
        {
            String clazz = line[0];
            String obfName = line[1];
            String sifnature = line[2];
            String deobfName = line[3];

            mappings.methods.computeIfAbsent(clazz, it -> Collections.singletonMap(MappingMethod.parse(deobfName, sifnature), obfName));
        }
    }
    public String processDesc(String desc)
    {
        if (desc.startsWith("L"))
            return type2desc(this.mapClass(desc.substring(1, desc.length() - 1).replace('/', '.')));
        else if (desc.startsWith("["))
            return "[" + processDesc(desc.substring(1));
        else
            return desc;
    }
    public static String type2desc(String type)
    {
        if (type.endsWith("[]"))
        {
            return '[' + type2desc(type.substring(0, type.length() - 2));
        }
        switch (type)
        {
            case "byte":
                return "B";
            case "char":
                return "C";
            case "double":
                return "D";
            case "float":
                return "F";
            case "int":
                return "I";
            case "long":
                return "J";
            case "short":
                return "S";
            case "boolean":
                return "Z";
            case "void":
                return "V";
            default:
                return "L" + type.replace('.', '/') + ";";
        }
    }
    public Mappings reverse()
    {
        Mappings result=new Mappings();
        for (Map.Entry<String, String> e : this.classes.entrySet())
        {
            result.classes.put(e.getValue(), e.getKey());
        }
        for (Map.Entry<String, Map<String, String>> e : this.fields.entrySet())
        {
            String tar = this.classes.get(e.getKey());
            Map<String, String> rf = result.fields.computeIfAbsent(tar, it -> new HashMap<>());
            for (Map.Entry<String, String> e1 : e.getValue().entrySet())
            {
                rf.put(e1.getValue(), e1.getValue());
            }
        }
        for (Map.Entry<String, Map<MappingMethod, String>> e : this.methods.entrySet())
        {
            String tar = this.classes.get(e.getKey());
            Map<MappingMethod, String> rm = result.methods.computeIfAbsent(tar, it -> new HashMap<>());
            for (Map.Entry<MappingMethod, String> e1 : e.getValue().entrySet())
            {
                rm.put(new MappingMethod(e1.getValue(), Arrays.stream(e1.getKey().parameterTypes).map(this::processDesc).toArray(String[]::new)), e1.getKey().name);
            }
        }
        return result;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> i : classes.entrySet())
        { sb.append(i.getKey()).append(" -> ").append(i.getValue()).append('\n'); }
        for (Map.Entry<String, Map<String, String>> i : fields.entrySet())
        {
            sb.append(i.getKey()).append(":\n");
            for (Map.Entry<String, String> j : i.getValue().entrySet())
            { sb.append("\t").append(j.getKey()).append(" -> ").append(j.getValue()).append('\n'); }
        }
        for (Map.Entry<String, Map<MappingMethod, String>> i : methods.entrySet())
        {
            sb.append(i.getKey()).append(":\n");
            for (Map.Entry<MappingMethod, String> j : i.getValue().entrySet())
            { sb.append("\t").append(j.getKey()).append(" -> ").append(j.getValue()).append('\n'); }
        }
        return sb.toString();
    }
}
