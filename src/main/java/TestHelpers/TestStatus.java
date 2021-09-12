package TestHelpers;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestStatus implements AfterTestExecutionCallback { // to jest rozszerzenie, z którego będziemy chcieli skorzystać podczas testu. Będziemy potrzebowali mieć obiekt klasy TestStatus i potem pobrać sobie isFailed dla tego obiektu
    public boolean isFailed; // tworzymy zmienną, która będzie przechowywała info o tym, czy test sfailował czy nie
    @Override
    public void afterTestExecution (ExtensionContext extensionContext) throws Exception {
        if (extensionContext.getExecutionException().isPresent()) { // parametr ExtensionContext zawiera wiele różnych informacji o teście, między innymi czy rzucony był jakiś wyjątek podczas testu. Jeśli był, to isFailed przyjmuje wartość true
            isFailed = true;
        }
        else isFailed = false;
    }
}
