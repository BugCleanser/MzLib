package mz.mzlib.minecraft.mappings;

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
    public String mapClass0(String from)
    {
        String result=this.classes.get(from);
        if(result!=null)
            return result;
        int index=from.lastIndexOf('$');
        if(index!=-1)
            return this.mapClass(from.substring(0,index))+from.substring(index);
        return null;
    }

    @Override
    public String mapField0(String fromClass,String fromField)
    {
        return Optional.ofNullable(this.fields.get(fromClass)).map(it->it.getOrDefault(fromField,fromField)).orElse(null);
    }

    @Override
    public String mapMethod0(String fromClass, MappingMethod fromMethod)
    {
        return Optional.ofNullable(this.methods.get(fromClass)).map(it->it.getOrDefault(fromMethod,fromMethod.name)).orElse(null);
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
                    Iterator<String> it = new BufferedReader(new StringReader(new String(Util.readInputStream(mappings), StandardCharsets.UTF_8))).lines().filter(_it -> !_it.trim().startsWith("COMMENT")).iterator();
                    List<String> fromPath=new ArrayList<>(),toPath=new ArrayList<>();
                    Supplier<String> from=()->String.join("$",fromPath),to=()->String.join("$",toPath);
                    while ( it.hasNext() )
                    {
                        String line = it.next();
                        int tab=0;
                        while(tab<line.length()&&line.charAt(tab)=='\t')
                            tab++;
                        String[] a = line.substring(tab).split(" ");
                        while(fromPath.size()>tab)
                            fromPath.remove(fromPath.size()-1);
                        while(toPath.size()>tab)
                            toPath.remove(toPath.size()-1);
                        switch (a[0])
                        {
                            case "CLASS":
                                fromPath.add(a[1].replace('/','.'));
                                if (a.length == 3)
                                {
                                    toPath.add(a[2].replace('/', '.'));
                                    result.classes.put(from.get(), to.get());
                                }
                                else
                                    result.classes.put(from.get(), from.get());
                                break;
                            case "FIELD":
                                result.fields.computeIfAbsent(from.get(), it1 -> new HashMap<>()).put(a[1], a[2]);
                                break;
                            case "METHOD":
                                if (a.length >= 4)
                                    result.methods.computeIfAbsent(from.get(), it1 -> new HashMap<>()).put(MappingMethod.parse(a[1], a[3]), a[2]);
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
                    break;
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
    public static Mappings parseSpigot(int version, SpigotMappings mappings)
    {
        Mappings result = new Mappings();
        new BufferedReader(new StringReader(mappings.classMappings)).lines()
                .filter(l -> !l.startsWith("#"))
                .forEach(line -> {
                    String obfName = line.split(" ")[0].replace('/', '.');;
                    String deobfName = line.split(" ")[1].replace('/', '.');
                    if(version==1605 && deobfName.contains("/"))
                        deobfName=deobfName.substring(deobfName.lastIndexOf('/')+1);
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
            String clazz = line[0].replace('/', '.');
            String obfName = line[1];
            String deobfName = line[2];

            mappings.fields.computeIfAbsent(clazz, it->new HashMap<>()).put(deobfName,obfName);
        }
        else if (line.length == 4)
        {
            String clazz = line[0].replace('/', '.');
            String obfName = line[1];
            String signature = line[2];
            String deobfName = line[3];

            mappings.methods.computeIfAbsent(clazz, it -> new HashMap<>()).put(MappingMethod.parse(deobfName, signature), obfName);
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
            String tar = this.mapClass(e.getKey());
            Map<String, String> rf = result.fields.computeIfAbsent(tar, it -> new HashMap<>());
            for (Map.Entry<String, String> e1 : e.getValue().entrySet())
            {
                rf.put(e1.getValue(), e1.getKey());
            }
        }
        for (Map.Entry<String, Map<MappingMethod, String>> e : this.methods.entrySet())
        {
            String tar = this.mapClass(e.getKey());
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
