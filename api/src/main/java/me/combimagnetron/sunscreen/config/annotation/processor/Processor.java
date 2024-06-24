package me.combimagnetron.sunscreen.config.annotation.processor;

import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;
import me.combimagnetron.sunscreen.config.Config;
import me.combimagnetron.sunscreen.config.annotation.*;
import me.combimagnetron.sunscreen.config.annotation.Optional;
import me.combimagnetron.sunscreen.config.element.Node;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Processor {

    public static <T> T load(Class<T> clazz, Path path) {
        final Map<Class<?>, Object> arguments = new LinkedHashMap<>();
        final Config template = transform(clazz);
        final Set<Class<?>> fields = Arrays.stream(clazz.getEnclosingConstructor().getParameterTypes()).collect(Collectors.toSet());
        final Set<Object> objects = new HashSet<>();
        final com.typesafe.config.Config config = ConfigFactory.parseFile(path.toFile());
        for (Node<?> node : template.nodes()) {
            Object object = config.getAnyRef(node.name());
            arguments.put(node.type(), object);
        }
        try {
            return clazz.getDeclaredConstructor(arguments.keySet().toArray(new Class[0])).newInstance(arguments.values().toArray());
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Config read(Path path) {
        ConfigObject object = ConfigFactory.parseFile(path.toFile()).root();
        Config config = Config.config();
        object.unwrapped().forEach(((string, o) -> config.node(Node.required(string, o))));
        return config;
    }

    @NotNull
    private static Config transform(Class<?> clazz) {
        Config config = Config.config();
        if (!clazz.isAnnotationPresent(me.combimagnetron.sunscreen.config.annotation.Config.class)) {
            return config;
        }
        Set<Field> fields = Arrays.stream(clazz.getFields()).filter(field -> !field.isAnnotationPresent(Excluded.class)).collect(Collectors.toSet());
        for (Field field : fields) {
            final ProcessedField<?> processedField = ProcessedField.from(field);
            if (field.isAnnotationPresent(Optional.class)) {
                config.node(Node.optional(processedField.name(), processedField.type()));
            } else if (field.isAnnotationPresent(Required.class)) {
                config.node(Node.required(processedField.name(), processedField.type()));
            } else if (field.isAnnotationPresent(Anonymous.class)) {
                config.node(Node.anonymous(processedField.type()));
            }

        }
        return config;
    }

    record ProcessedField<T>(String name, Class<T> type) {

        public static <V> ProcessedField<V> from(Field field) {
            String name = field.isAnnotationPresent(Name.class) ? field.getAnnotation(Name.class).name() : field.getName();
            Class<V> type = (Class<V>) field.getType();
            return new ProcessedField<>(name, type);
        }

    }

}
