package listners;

import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.IAnnotationTransformer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class AnnotationTransformaer implements IAnnotationTransformer {
    // testng will apply this retry analyzer for all failed test cases
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod, Class<?> occurringClazz) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
