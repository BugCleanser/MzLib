package mz.mzlib.minecraft.mappings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MappingMethod
{
    public String name;
    public String[] parameterTypes;

    public MappingMethod(String name, String[] parameterTypes)
    {
        this.name = name;
        this.parameterTypes = parameterTypes;
    }

    public static MappingMethod parse(String name, String sig)
    {        String returnType = sig.substring(sig.indexOf(")") + 1);
        List<String> params = new ArrayList<>();
        String rawParams = sig.substring(1, sig.indexOf(")"));

        int i = 0;
        while (i < rawParams.length())
        {
            char current = rawParams.charAt(i);
            if (current == 'L')
            {
                int semicolonIndex = rawParams.indexOf(';', i);
                params.add(rawParams.substring(i, semicolonIndex + 1));
                i = semicolonIndex + 1;
            }
            else if (current == '[')
            {
                i++;
                char arrayType = rawParams.charAt(i);
                if (arrayType == 'L')
                {
                    int semicolonIndex = rawParams.indexOf(';', i);
                    params.add("[" + rawParams.substring(i, semicolonIndex + 1));
                    i = semicolonIndex + 1;
                }
                else
                {
                    params.add("[" + arrayType);
                    i++;
                }
            }
            else
            {
                params.add(String.valueOf(current));
                i++;
            }
        }
        return new MappingMethod(name, params.toArray(new String[0]));
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof MappingMethod))
            return false;
        MappingMethod that = (MappingMethod) o;
        return Objects.equals(name, that.name) && Arrays.equals(parameterTypes, that.parameterTypes);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, Arrays.hashCode(parameterTypes));
    }

    @Override
    public String toString()
    {
        return name + "(" + String.join("", parameterTypes) + ")";
    }
}
