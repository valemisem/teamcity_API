package teamcity.api.generators;

import teamcity.api.annotations.Optional;
import teamcity.api.annotations.Parameterizable;
import teamcity.api.annotations.Random;
import teamcity.api.models.BaseModel;
import teamcity.api.models.TestData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class TestDataGenerator {

    private TestDataGenerator() {
    }


    public static <T extends BaseModel> T generate(List<BaseModel> generatedModels, Class<T> generatorClass,
                                                   Object... parameters) {
        try {
            var instance = generatorClass.getDeclaredConstructor().newInstance();
            for (var field : generatorClass.getDeclaredFields()) {
                field.setAccessible(true);
                if (!field.isAnnotationPresent(Optional.class)) {
                    var generatedClass = generatedModels.stream().filter(m
                            -> m.getClass().equals(field.getType())).findFirst();
                    if (field.isAnnotationPresent(Parameterizable.class) && parameters.length > 0) {
                        field.set(instance, parameters[0]);
                        parameters = Arrays.copyOfRange(parameters, 1, parameters.length);
                    } else if (field.isAnnotationPresent(Random.class)) {
                        if (String.class.equals(field.getType())) {
                            field.set(instance, RandomData.getString());
                        }
                    } else if (BaseModel.class.isAssignableFrom(field.getType())) {
                        var finalParameters = parameters;
                        field.set(instance, generatedClass.orElseGet(() -> generate(
                                generatedModels, field.getType().asSubclass(BaseModel.class), finalParameters)));
                    } else if (List.class.isAssignableFrom(field.getType())) {
                        if (field.getGenericType() instanceof ParameterizedType pt) {
                            var typeClass = (Class<?>) pt.getActualTypeArguments()[0];
                            if (BaseModel.class.isAssignableFrom(typeClass)) {
                                var finalParameters = parameters;
                                field.set(instance, generatedClass.map(List::of).orElseGet(() -> List.of(generate(
                                        generatedModels, typeClass.asSubclass(BaseModel.class), finalParameters))));
                            }
                        }
                    }
                }
                field.setAccessible(false);
            }
            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                 | NoSuchMethodException e) {
            throw new IllegalStateException("Cannot generate test data", e);
        }
    }

    public static TestData generate() {
        // Идем по всем полям TestData и для каждого, кто наследник BaseModel вызываем generate() c передачей уже сгенерированных сущностей
        try {
            // Здесь создаётся новый экземпляр класса TestData с использованием рефлексии. Это позволяет обойтись без явного вызова конструктора, даже если он приватный
            var instance = TestData.class.getDeclaredConstructor().newInstance();
            var generatedModels = new ArrayList<BaseModel>();
            for (var field : TestData.class.getDeclaredFields()) {
                field.setAccessible(true); // теперь можем обращаться к private полю
                // Можем теперь обращаться к типу поля, так как у нас стоит field.setAccessible(true)
                if (BaseModel.class.isAssignableFrom(field.getType())) { // Проверяем, является ли тип поля наследником класса BaseModel
                    var generatedModel = generate(generatedModels, field.getType().asSubclass(BaseModel.class));
                    field.set(instance, generatedModel); // Этому field этого объекта instance TestData мы проставляем generatedModel
                    generatedModels.add(generatedModel);
                }
                field.setAccessible(false);
            }
            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new IllegalStateException("Cannot generate a test data", e);
        }
    }


    public static <T extends BaseModel> T generate(Class<T> generatorClass, Object... parameters) {
        return generate(Collections.emptyList(), generatorClass, parameters);
    }
}
