// This class belongs to the com.example package with the rest of the lesson.
package com.example;

// AnnotationConfigApplicationContext is the Spring IoC container we create in main().
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Main demonstrates the Spring workflow, bean lifecycle, singleton scope,
 * prototype scope, and dataflow between objects.
 */
public class Main {

    // Java starts running this program from the main method.
    public static void main(String[] args) {
        // Print a section heading for the first part of the lesson.
        printTitle("SPRING WORKFLOW LESSON");
        System.out.println("Step 1: main() starts.");
        System.out.println("        We did NOT manually create DatabaseService, UserService, or ShoppingCart.");
        System.out.println("        This is the main idea of IoC: our code gives control of object creation to Spring.");
        System.out.println("        IoC means Inversion of Control.");
        System.out.println("        Normal Java: Main controls objects with new DatabaseService().");
        System.out.println("        Spring IoC: Spring controls objects, and Main asks Spring for them.");
        System.out.println("Step 2: We create the Spring container using AnnotationConfigApplicationContext(AppConfig.class).");
        System.out.println("        This container is the IoC container.");
        System.out.println("        It reads AppConfig annotations and prepares the beans.\n");

        // This line creates the Spring IoC container and gives it AppConfig as the setup class.
        AnnotationConfigApplicationContext context =
                // Spring reads @Configuration, @ComponentScan, @Bean, @Component, @Service, and @Scope from here.
                new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("\nStep 3: Spring container is ready.");
        System.out.println("        During startup, Spring already created singleton beans.");
        System.out.println("        Prototype beans are different: Spring waits until we ask for one.\n");
        System.out.println("IoC dataflow:");
        System.out.println("        Main -> Spring container: please manage my objects.");
        System.out.println("        Spring container -> objects: create, initialize, store, destroy.");
        System.out.println("        Main -> Spring container: getBean(...) when I need an object.\n");

        // Print the concept sections before using the beans.
        printIoCConcept();
        // Print what the main Spring annotations mean.
        printAnnotationConcepts();
        // Print extra detail about @Scope and ConfigurableBeanFactory constants.
        printScopeConcepts();
        // Print which lesson beans Spring registered.
        printBeanMap(context);

        printTitle("USER SERVICE LIFECYCLE TEST (@Bean)");
        System.out.println("Dataflow: Main asks Spring for UserService.");
        System.out.println("Logic: UserService came from AppConfig.userService(), because that method has @Bean.");
        System.out.println("IoC point: Main does NOT call new UserService(). Spring already controls that object.");
        System.out.println("@Bean point: @Bean is used on a METHOD. The method returns the object Spring will manage.");
        // Ask Spring for the UserService bean instead of writing new UserService().
        UserService userService = context.getBean(UserService.class);
        System.out.println("Main received UserService object: " + objectLabel(userService));
        System.out.println("Now Main calls a business method on that object.");
        // Call a normal business method on the object Spring returned.
        userService.createUser("Jawaher");

        printTitle("SINGLETON TEST (DatabaseService)");
        System.out.println("DatabaseService uses @Service.");
        System.out.println("@Service means Spring discovers this class during @ComponentScan.");
        System.out.println("@Service is a specialized @Component for service/business logic classes.");
        System.out.println("Default scope is singleton, so Spring creates ONE object and reuses it.\n");
        System.out.println("IoC point: DatabaseService is not created by Main.");
        System.out.println("           It is created by Spring during container startup, then stored inside the IoC container.\n");

        System.out.println("Main -> Spring: getBean(DatabaseService.class) request #1");
        // Ask Spring for DatabaseService the first time.
        DatabaseService db1 = context.getBean(DatabaseService.class);
        System.out.println("Spring -> Main: returned " + objectLabel(db1) + " with business ID " + db1.getMyId());

        System.out.println("\nMain -> Spring: getBean(DatabaseService.class) request #2");
        // Ask Spring for DatabaseService the second time.
        DatabaseService db2 = context.getBean(DatabaseService.class);
        System.out.println("Spring -> Main: returned " + objectLabel(db2) + " with business ID " + db2.getMyId());

        System.out.println("\nMain -> Spring: getBean(DatabaseService.class) request #3");
        // Ask Spring for DatabaseService the third time.
        DatabaseService db3 = context.getBean(DatabaseService.class);
        System.out.println("Spring -> Main: returned " + objectLabel(db3) + " with business ID " + db3.getMyId());

        System.out.println("\ndb1 ID = " + db1.getMyId());
        System.out.println("db2 ID = " + db2.getMyId());
        System.out.println("db3 ID = " + db3.getMyId());
        System.out.println("Are db1 and db2 the SAME Java object? " + (db1 == db2));
        System.out.println("Are db2 and db3 the SAME Java object? " + (db2 == db3));
        System.out.println("Conclusion: singleton gives us the SAME Java object every time.");

        printTitle("PROTOTYPE TEST (ShoppingCart)");
        System.out.println("ShoppingCart uses @Component and @Scope(\"prototype\").");
        System.out.println("In code we used @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE).");
        System.out.println("That constant means the same thing as @Scope(\"prototype\"), but it avoids spelling mistakes.");
        System.out.println("@Component means Spring discovers it during scanning.");
        System.out.println("@Component is the general annotation: this class is a Spring-managed component.");
        System.out.println("prototype means Spring creates a NEW object every time getBean() is called.\n");
        System.out.println("IoC point: Main does not decide HOW to create the cart.");
        System.out.println("           Main only asks Spring. Spring applies the prototype rule.\n");

        System.out.println("Main -> Spring: getBean(ShoppingCart.class) request #1");
        // Ask Spring for the first ShoppingCart prototype bean.
        ShoppingCart cart1 = context.getBean(ShoppingCart.class);
        System.out.println("Spring -> Main: returned " + objectLabel(cart1) + " with cart ID " + cart1.getCartId());

        System.out.println("\nMain -> Spring: getBean(ShoppingCart.class) request #2");
        // Ask Spring for the second ShoppingCart prototype bean.
        ShoppingCart cart2 = context.getBean(ShoppingCart.class);
        System.out.println("Spring -> Main: returned " + objectLabel(cart2) + " with cart ID " + cart2.getCartId());

        System.out.println("\nMain -> Spring: getBean(ShoppingCart.class) request #3");
        // Ask Spring for the third ShoppingCart prototype bean.
        ShoppingCart cart3 = context.getBean(ShoppingCart.class);
        System.out.println("Spring -> Main: returned " + objectLabel(cart3) + " with cart ID " + cart3.getCartId());

        System.out.println("\ncart1 ID = " + cart1.getCartId());
        System.out.println("cart2 ID = " + cart2.getCartId());
        System.out.println("cart3 ID = " + cart3.getCartId());
        System.out.println("Are cart1 and cart2 the SAME Java object? " + (cart1 == cart2));
        System.out.println("Are cart2 and cart3 the SAME Java object? " + (cart2 == cart3));
        System.out.println("Conclusion: prototype gives us a NEW Java object every time.");

        printTitle("DATAFLOW SIMULATION (Shopping Carts)");
        System.out.println("Main -> Spring: create a cart for Jawaher.");
        // Ask Spring for a cart object that belongs to Jawaher.
        ShoppingCart jawaherCart = context.getBean(ShoppingCart.class);
        System.out.println("Main -> Spring: create a cart for Omar.");
        // Ask Spring for a separate cart object that belongs to Omar.
        ShoppingCart omarCart = context.getBean(ShoppingCart.class);

        System.out.println("\nDataflow: item strings move from Main into each customer's own cart object.");
        System.out.println("Main -> Jawaher's cart: addItem(\"iPhone 15\")");
        // Put item data into Jawaher's cart object.
        jawaherCart.addItem("iPhone 15");
        System.out.println("Main -> Jawaher's cart: addItem(\"AirPods\")");
        // Put more item data into Jawaher's cart object.
        jawaherCart.addItem("AirPods");
        System.out.println("Main -> Omar's cart: addItem(\"PlayStation 5\")");
        // Put item data into Omar's separate cart object.
        omarCart.addItem("PlayStation 5");

        System.out.println("\nJawaher's cart (#" + jawaherCart.getCartId() + "): " + jawaherCart.getItems());
        System.out.println("Omar's cart (#" + omarCart.getCartId() + "): " + omarCart.getItems());
        System.out.println("Conclusion: each customer has their OWN cart object, so the data stays separated.");

        System.out.println("\nIf ShoppingCart were singleton, Omar would see Jawaher's items too.");
        System.out.println("That is why shopping carts should be prototype or request/session scoped.");

        printTitle("SHUTDOWN");
        System.out.println("Main -> Spring: context.close()");
        System.out.println("Spring will now destroy singleton beans and call @PreDestroy where available.");
        System.out.println("IoC point: Spring also controls the shutdown lifecycle of singleton beans.");
        // Close the Spring container so singleton beans can run @PreDestroy cleanup methods.
        context.close();
        System.out.println("Program finished.");
    }

    // Helper method for printing clear section titles in the terminal.
    private static void printTitle(String title) {
        System.out.println("\n==================================================");
        System.out.println(title);
        System.out.println("==================================================");
    }

    // Helper method that prints the important lesson beans registered in the Spring container.
    private static void printBeanMap(AnnotationConfigApplicationContext context) {
        printTitle("WHAT SPRING KNOWS");
        System.out.println("Spring has these lesson beans registered:");
        System.out.println("- UserService: created by @Bean method in AppConfig");
        System.out.println("- DatabaseService: discovered by @Service and @ComponentScan");
        System.out.println("- ShoppingCart: discovered by @Component and @ComponentScan; scope is prototype");
        System.out.println("\nBean names inside Spring:");
        // Loop through every bean definition name Spring knows.
        for (String beanName : context.getBeanDefinitionNames()) {
            // Only print the lesson beans, not every internal Spring bean.
            if (beanName.contains("userService")
                    || beanName.contains("databaseService")
                    || beanName.contains("shoppingCart")
                    || beanName.contains("appConfig")) {
                System.out.println("- " + beanName);
            }
        }
    }

    // Helper method that explains IoC before the object examples.
    // The parentheses are empty because this method does not need outside input.
    // Parentheses are where method parameters go.
    // Example with no parameter: printIoCConcept()
    // Example with one parameter: printIoCConcept(String studentName)
    // Example with two parameters: printIoCConcept(String topic, int lessonNumber)
    // This method prints fixed text, so we leave the parentheses empty.
    private static void printIoCConcept() {
        printTitle("IOC CONCEPT (INVERSION OF CONTROL)");
        System.out.println("Without IoC:");
        System.out.println("Main controls everything:");
        System.out.println("    DatabaseService db = new DatabaseService();");
        System.out.println("    ShoppingCart cart = new ShoppingCart();");
        System.out.println("Main must know how to create every dependency.\n");

        System.out.println("With Spring IoC:");
        System.out.println("Spring controls object creation and lifecycle.");
        System.out.println("Main only asks:");
        System.out.println("    DatabaseService db = context.getBean(DatabaseService.class);");
        System.out.println("This is why we say control is inverted:");
        System.out.println("    control moves from your code to the Spring container.");
    }

    // Helper method that explains the Spring annotations used in this lesson.
    private static void printAnnotationConcepts() {
        printTitle("ANNOTATION GUIDE");
        System.out.println("@Configuration");
        System.out.println("    Used on a class.");
        System.out.println("    Means: this class is a Spring configuration/instruction class.");
        System.out.println("    In this project: AppConfig has @Configuration.");
        System.out.println("    Spring reads AppConfig to learn how to set up the container.\n");

        System.out.println("@ComponentScan(\"com.example\")");
        System.out.println("    Used on the configuration class.");
        System.out.println("    Means: Spring should scan the com.example package.");
        System.out.println("    During scanning, Spring looks for classes marked @Component, @Service, etc.\n");

        System.out.println("@Component");
        System.out.println("    Used on a class.");
        System.out.println("    Means: this class is a Spring bean candidate.");
        System.out.println("    Spring can create and manage an object from this class automatically.");
        System.out.println("    In this project: ShoppingCart has @Component.");
        System.out.println("    Use @Component when the class is a general Spring-managed object.\n");

        System.out.println("@Service");
        System.out.println("    Used on a class.");
        System.out.println("    Means: this class is a service/business-logic component.");
        System.out.println("    Important: @Service works like @Component, but gives the class a clearer meaning.");
        System.out.println("    In this project: DatabaseService has @Service.");
        System.out.println("    Use @Service when the class performs application logic, data access logic, or business work.\n");

        System.out.println("@Scope");
        System.out.println("    Used on a Spring bean class or @Bean method.");
        System.out.println("    Means: tells Spring HOW MANY objects to create and WHEN to reuse them.");
        System.out.println("    Default scope is singleton: one object shared every time.");
        System.out.println("    Prototype scope means: create a new object every time getBean() is called.");
        System.out.println("    In this project: ShoppingCart has @Scope(\"prototype\").");
        System.out.println("    In the actual code we wrote ConfigurableBeanFactory.SCOPE_PROTOTYPE.");
        System.out.println("    That is just Spring's constant for the text \"prototype\".");
        System.out.println("    That is why every customer gets a different cart object.\n");

        System.out.println("@Bean");
        System.out.println("    Used on a method inside a @Configuration class.");
        System.out.println("    Means: Spring should run this method and manage the object returned from it.");
        System.out.println("    In this project: AppConfig.userService() has @Bean and returns new UserService().\n");

        System.out.println("Quick difference: @Service vs @Bean");
        System.out.println("    @Service: put it on the class, and Spring finds the class by scanning.");
        System.out.println("    @Bean: put it on a method, and YOU write the method that creates/returns the object.");
        System.out.println("    Both create Spring beans. They are two different ways to register objects in Spring.");
        System.out.println("\nQuick difference: @Component vs @Service");
        System.out.println("    @Component: general Spring-managed class.");
        System.out.println("    @Service: also a Spring-managed class, but specifically for service/business logic.");
        System.out.println("    Technically Spring treats both as components during scanning.");
        System.out.println("    The difference is meaning/readability for humans and project organization.");
    }

    // Helper method that explains @Scope and ConfigurableBeanFactory constants.
    private static void printScopeConcepts() {
        printTitle("SCOPE AND CONFIGURABLEBEANFACTORY");
        System.out.println("The annotation is @Scope(...).");
        System.out.println("    @Scope is the Spring instruction that changes bean scope.");
        System.out.println("    It answers: should Spring reuse one object, or create new objects?\n");

        System.out.println("ConfigurableBeanFactory.SCOPE_PROTOTYPE is NOT a separate scope system.");
        System.out.println("    It is just a constant provided by Spring.");
        System.out.println("    Its value is the string \"prototype\".");
        System.out.println("    So these two lines mean the same thing:");
        System.out.println("        @Scope(\"prototype\")");
        System.out.println("        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)");
        System.out.println("    The constant is safer because Java catches typos.");
        System.out.println("    Example typo: @Scope(\"prototpye\") would compile, but Spring would not understand it.\n");

        System.out.println("Common values you can put inside @Scope:");
        System.out.println("    @Scope(\"singleton\")");
        System.out.println("        One shared object. This is the default.");
        System.out.println("        Constant form: ConfigurableBeanFactory.SCOPE_SINGLETON");
        System.out.println("    @Scope(\"prototype\")");
        System.out.println("        New object every time you ask Spring with getBean().");
        System.out.println("        Constant form: ConfigurableBeanFactory.SCOPE_PROTOTYPE");
        System.out.println("    @Scope(\"request\")");
        System.out.println("        One object per HTTP request in a Spring web app.");
        System.out.println("    @Scope(\"session\")");
        System.out.println("        One object per user's HTTP session in a Spring web app.");
        System.out.println("    @Scope(\"application\")");
        System.out.println("        One object for the whole web application.");
        System.out.println("    @Scope(\"websocket\")");
        System.out.println("        One object per WebSocket session in a Spring web app.\n");

        System.out.println("For this console lesson, singleton and prototype are the important ones.");
        System.out.println("Web scopes need a web-aware Spring ApplicationContext.");
    }

    // Helper method that prints a Java object's class name and memory identity hash.
    private static String objectLabel(Object object) {
        // identityHashCode helps show whether two references point to the same Java object.
        return object.getClass().getSimpleName() + "@"
                + Integer.toHexString(System.identityHashCode(object));
    }
}
