package com.github.hokutomc.lib.nbt;

import com.github.hokutomc.lib.data.enumerate.HT_I_IntOrdered;
import com.github.hokutomc.lib.data.enumerate.HT_I_StringOrdered;
import com.github.hokutomc.lib.util.HT_ArrayUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static com.github.hokutomc.lib.nbt.HT_NBTUtil.*;

/**
 * Created by user on 2015/02/15.
 */
public final class HT_NBTAnnotations {
    private HT_NBTAnnotations (){}

    private static class NBTActions<A extends Annotation, T> {
        final Class<A> annotationClass;
        final Class<T> fieldType;
        private boolean allowExtends;
        final NBTWrite<T> write;
        final NBTRead<T> read;

        private NBTActions (Class<A> annotationClass, Class<T> fieldType, boolean allowExtends, NBTWrite<T> write, NBTRead<T> read) {
            this.annotationClass = annotationClass;
            this.fieldType = fieldType;
            this.allowExtends = allowExtends;
            this.write = write;
            this.read = read;
        }

        private A getAnnotation(Field field) {
            return field.getAnnotation(annotationClass);
        }

        private boolean isAcceptable (Field field) {
            A ann = getAnnotation(field);
            if (ann != null) {
                if (!allowExtends && fieldType == field.getType()) {return true;}
                else if (allowExtends && fieldType.isAssignableFrom(field.getType())) {return true;}
            }
            return false;
        }

        @SuppressWarnings("unchecked")
        private void readFromNBT (Object obj, Field field, NBTTagCompound nbtTagCompound) {
            A ann = getAnnotation(field);

            try {
                T fVal = (T) field.get(obj);

                field.set(obj, read.read(fVal, nbtTagCompound, getValue(ann), (T) getDefaultValue(fVal, ann)));
            } catch (IllegalAccessException | IllegalStateException e) {
                e.printStackTrace();
            }
        }

        @SuppressWarnings("unchecked")
        public void writeToNBT (Object obj, Field field, NBTTagCompound nbtTagCompound) {
            A ann = getAnnotation(field);
            T fVal = null;
            try {
                fVal = (T) field.get(obj);

            } catch (IllegalAccessException | IllegalStateException e) {
                e.printStackTrace();
            }
            write.write(fVal, nbtTagCompound, getValue(ann));
        }

    }

    private static String getValue (Annotation annotation) {
        if (annotation instanceof NBTBool) {
            return  ((NBTBool) annotation).value();
        } else if (annotation instanceof NBTByte) {
            return ((NBTByte) annotation).value();
        } else if (annotation instanceof NBTByteArray) {
            return ((NBTByteArray) annotation).value();
        } else if (annotation instanceof NBTDouble) {
            return ((NBTDouble) annotation).value();
        } else if (annotation instanceof NBTFloat) {
            return ((NBTFloat) annotation).value();
        } else if (annotation instanceof NBTInt) {
            return ((NBTInt) annotation).value();
        } else if (annotation instanceof NBTIntArray) {
            return ((NBTIntArray) annotation).value();
        } else if (annotation instanceof NBTIntOrdered) {
            return ((NBTIntOrdered) annotation).value();
        } else if (annotation instanceof NBTItemStackArray) {
            return ((NBTItemStackArray) annotation).value();
        } else if (annotation instanceof NBTLong) {
            return ((NBTLong) annotation).value();
        } else if (annotation instanceof NBTShort) {
            return ((NBTShort) annotation).value();
        } else if (annotation instanceof NBTString) {
            return ((NBTString) annotation).value();
        } else if (annotation instanceof NBTStringOrdered) {
            return ((NBTStringOrdered) annotation).value();
        }
        return null;
    }

    private static Object getDefaultValue (Object fieldValue, Annotation annotation) {

        if (annotation instanceof NBTBool) return ((NBTBool) annotation).defaultValue();
        if (annotation instanceof NBTByte) return ((NBTByte) annotation).defaultValue();
        if (annotation instanceof NBTByteArray) return ((NBTByteArray) annotation).defaultValue();
        if (annotation instanceof NBTDouble) return ((NBTDouble) annotation).defaultValue();
        if (annotation instanceof NBTFloat) return ((NBTFloat) annotation).defaultValue();
        if (annotation instanceof NBTInt) return ((NBTInt) annotation).defaultValue();
        if (annotation instanceof NBTIntArray) return ((NBTIntArray) annotation).defaultValue();
        if (annotation instanceof NBTItemStackArray) return new ItemStack[((NBTItemStackArray) annotation).size()];
        if (annotation instanceof NBTLong) return ((NBTLong) annotation).defaultValue();
        if (annotation instanceof NBTShort) return ((NBTShort) annotation).defaultValue();
        if (annotation instanceof NBTString) return ((NBTString) annotation).defaultValue();
        if (annotation instanceof NBTIntOrdered && fieldValue instanceof HT_I_IntOrdered)
            return ((HT_I_IntOrdered) fieldValue).getEnumerator().get(((NBTIntOrdered) annotation).defaultInt());
        if (annotation instanceof NBTStringOrdered && fieldValue instanceof HT_I_StringOrdered)
            return ((HT_I_StringOrdered) fieldValue).getEnumerator().get(((NBTStringOrdered) annotation).defaultString());
        else return null;
    }

    interface NBTWrite<T> {
        void write(T t, NBTTagCompound nbtTagCompound, String key);
    }

    interface NBTRead<T> {
        T read(T t, NBTTagCompound nbtTagCompound, String key, T alt);
    }


    private final static List<NBTActions> actionList;

    private final static Map<Class, Class<? extends Annotation>> finalTypes;
    private final static Map<Class, Class<? extends Annotation>> exTypes;

    private static <A extends Annotation, T> void add (Class<A> annotationClass, Class<T> fieldType, boolean allowExtends, NBTWrite<T> write, NBTRead<T> read) {
        actionList.add(new NBTActions<>(annotationClass, fieldType, allowExtends, write, read));
    }

    private static <A extends Annotation, T> void add (Class<A> annotationClass, Class<T> fieldType, NBTWrite<T> write, NBTRead<T> read) {
        actionList.add(new NBTActions<>(annotationClass, fieldType, false, write, read));
    }

    static {
        actionList = Lists.newArrayList();
        add(NBTBool.class, boolean.class,
                new NBTWrite<Boolean>() {
                    @Override
                    public void write (Boolean aBoolean, NBTTagCompound nbtTagCompound, String key) {
                        nbtTagCompound.setBoolean(key, aBoolean);
                    }
                },
                new NBTRead<Boolean>() {
                    @Override
                    public Boolean read (Boolean aBoolean, NBTTagCompound nbtTagCompound, String key, Boolean alt) {
                        return getBoolean(key, nbtTagCompound, alt);
                    }
                }
        );
        add(NBTByte.class, byte.class,
                new NBTWrite<Byte>() {
                    @Override
                    public void write (Byte aByte, NBTTagCompound nbtTagCompound, String key) {
                        nbtTagCompound.setByte(key, aByte);
                    }
                },
                new NBTRead<Byte>() {
                    @Override
                    public Byte read (Byte aByte, NBTTagCompound nbtTagCompound, String key, Byte alt) {
                        return getByte(key, nbtTagCompound, alt);
                    }
                }
        );
        add(NBTByteArray.class, byte[].class,
                new NBTWrite<byte[]>() {
                    @Override
                    public void write (byte[] bytes, NBTTagCompound nbtTagCompound, String key) {
                        nbtTagCompound.setByteArray(key, bytes);
                    }
                },
                new NBTRead<byte[]>() {
                    @Override
                    public byte[] read (byte[] bytes, NBTTagCompound nbtTagCompound, String key, byte[] alt) {
                        return getByteArray(key, nbtTagCompound, alt);
                    }
                }
        );
        add(NBTDouble.class, double.class,
                new NBTWrite<Double>() {
                    @Override
                    public void write (Double aDouble, NBTTagCompound nbtTagCompound, String key) {
                        nbtTagCompound.setDouble(key, aDouble);
                    }
                },
                new NBTRead<Double>() {
                    @Override
                    public Double read (Double aDouble, NBTTagCompound nbtTagCompound, String key, Double alt) {
                        return getDouble(key, nbtTagCompound, alt);
                    }
                }
        );
        add(NBTEnum.class, Enum.class, true,
                new NBTWrite<Enum>() {
                    @Override
                    public void write (Enum anEnum, NBTTagCompound nbtTagCompound, String key) {
                        writeEnum(key, nbtTagCompound, anEnum);
                    }
                },
                new NBTRead<Enum>() {
                    @Override
                    public Enum read (Enum anEnum, NBTTagCompound nbtTagCompound, String key, Enum alt) {
                        return getEnum(key, nbtTagCompound, alt);
                    }
                }
        );
        add(NBTFloat.class, float.class,
                new NBTWrite<Float>() {
                    @Override
                    public void write (Float aFloat, NBTTagCompound nbtTagCompound, String key) {
                        nbtTagCompound.setFloat(key, aFloat);
                    }
                },
                new NBTRead<Float>() {
                    @Override
                    public Float read (Float aFloat, NBTTagCompound nbtTagCompound, String key, Float alt) {
                        return getFloat(key, nbtTagCompound, alt);
                    }
                }
        );
        add(NBTInt.class, int.class,
                new NBTWrite<Integer>() {
                    @Override
                    public void write (Integer integer, NBTTagCompound nbtTagCompound, String key) {
                        nbtTagCompound.setInteger(key, integer);
                    }
                },
                new NBTRead<Integer>() {
                    @Override
                    public Integer read (Integer integer, NBTTagCompound nbtTagCompound, String key, Integer alt) {
                        return getInteger(key, nbtTagCompound, alt);
                    }
                }
        );
        add(NBTIntArray.class, int[].class,
                new NBTWrite<int[]>() {
                    @Override
                    public void write (int[] ints, NBTTagCompound nbtTagCompound, String key) {
                        nbtTagCompound.setIntArray(key, ints);
                    }
                },
                new NBTRead<int[]>() {
                    @Override
                    public int[] read (int[] ints, NBTTagCompound nbtTagCompound, String key, int[] alt) {
                        return getIntArray(key, nbtTagCompound, alt);
                    }
                }
        );
        add(NBTIntOrdered.class, HT_I_IntOrdered.class, true,
                new NBTWrite<HT_I_IntOrdered>() {
                    @Override
                    public void write (HT_I_IntOrdered ht_i_intOrdered, NBTTagCompound nbtTagCompound, String key) {
                        writeIntOrdered(key, nbtTagCompound, ht_i_intOrdered);
                    }
                },
                new NBTRead<HT_I_IntOrdered>() {
                    @Override
                    public HT_I_IntOrdered read (HT_I_IntOrdered ht_i_intOrdered, NBTTagCompound nbtTagCompound, String key, HT_I_IntOrdered alt) {
                        return getIntOrdered(key, nbtTagCompound, alt);
                    }
                }
        );
        add(NBTLong.class, long.class,
                new NBTWrite<Long>() {
                    @Override
                    public void write (Long aLong, NBTTagCompound nbtTagCompound, String key) {
                        nbtTagCompound.setLong(key, aLong);
                    }
                },
                new NBTRead<Long>() {
                    @Override
                    public Long read (Long aLong, NBTTagCompound nbtTagCompound, String key, Long alt) {
                        return getLong(key, nbtTagCompound, alt);
                    }
                }
        );
        add(NBTShort.class, short.class,
                new NBTWrite<Short>() {
                    @Override
                    public void write (Short aShort, NBTTagCompound nbtTagCompound, String key) {
                        nbtTagCompound.setShort(key, aShort);
                    }
                },
                new NBTRead<Short>() {
                    @Override
                    public Short read (Short aShort, NBTTagCompound nbtTagCompound, String key, Short alt) {
                        return getShort(key, nbtTagCompound, alt);
                    }
                }
        );
        add(NBTString.class, String.class,
                new NBTWrite<String>() {
                    @Override
                    public void write (String str, NBTTagCompound nbtTagCompound, String key) {
                        nbtTagCompound.setString(key, str);
                    }
                },
                new NBTRead<String>() {
                    @Override
                    public String read (String str, NBTTagCompound nbtTagCompound, String key, String alt) {
                        return getString(key, nbtTagCompound, alt);
                    }
                }
        );
        add(NBTStringOrdered.class, HT_I_StringOrdered.class, true,
                new NBTWrite<HT_I_StringOrdered>() {
                    @Override
                    public void write (HT_I_StringOrdered stringOrdered, NBTTagCompound nbtTagCompound, String key) {
                        writeStringOrdered(key, nbtTagCompound, stringOrdered);
                    }
                },
                new NBTRead<HT_I_StringOrdered>() {
                    @Override
                    public HT_I_StringOrdered read (HT_I_StringOrdered stringOrdered, NBTTagCompound nbtTagCompound, String key, HT_I_StringOrdered alt) {
                        return getStringOrdered(key, nbtTagCompound, alt);
                    }
                }
        );


        finalTypes = Maps.newHashMap();
        putF(boolean.class, NBTBool.class);
        putF(byte.class, NBTByte.class);
        putF(byte[].class, NBTByteArray.class);
        putF(double.class, NBTDouble.class);
        putF(float.class, NBTFloat.class);
        putF(int.class, NBTInt.class);
        putF(int[].class, NBTIntArray.class);
        putF(ItemStack[].class, NBTItemStackArray.class);
        putF(long.class, NBTLong.class);
        putF(short.class, NBTShort.class);
        putF(String.class, NBTString.class);
        exTypes = Maps.newHashMap();
        putX(Enum.class, NBTEnum.class);
        putX(HT_I_IntOrdered.class, NBTIntOrdered.class);
        putX(HT_I_StringOrdered.class, NBTStringOrdered.class);
    }

    private static void putF (Class key, Class<? extends Annotation> value) {
        finalTypes.put(key, value);
    }

    private static void putX (Class key, Class<? extends Annotation> value) {
        exTypes.put(key, value);
    }

    private static Field[] getAnnotatedFields(Class clazz) {
        List<Field> ret = Lists.newArrayList();
        for (Field field : clazz.getDeclaredFields()) {
            for (NBTActions actions : actionList) {
                if (actions.isAcceptable(field)) {
                    ret.add(field);
                }
            }
        }
        return HT_ArrayUtil.toArray(ret);
    }

    public static void readFieldsFromNBT (Object obj, NBTTagCompound nbt) {
        Field[] annotated = getAnnotatedFields(obj.getClass());
        for (Field field : annotated) {
            for (NBTActions actions : actionList) {
                if (actions.isAcceptable(field)) actions.readFromNBT(obj, field, nbt);
            }
        }
    }

    public static void writeFieldsToNBT (Object obj, NBTTagCompound nbt) {
        for (Field field : getAnnotatedFields(obj.getClass())) {
            for (NBTActions actions : actionList) {
                if (actions.isAcceptable(field)) actions.writeToNBT(obj, field, nbt);
            }
        }
    }

}
