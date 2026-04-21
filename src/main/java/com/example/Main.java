package com.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Main demonstrates the Spring workflow, bean lifecycle, singleton scope,
 * prototype scope, and dataflow between objects.
 */
public class Main {

    public static void main(String[] args) {
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

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("\nStep 3: Spring container is ready.");
        System.out.println("        During startup, Spring already created singleton beans.");
        System.out.println("        Prototype beans are different: Spring waits until we ask for one.\n");
        System.out.println("IoC dataflow:");
        System.out.println("        Main -> Spring container: please manage my objects.");
        System.out.println("        Spring container -> objects: create, initialize, store, destroy.");
        System.out.println("        Main -> Spring container: getBean(...) when I need an object.\n");

        printIoCConcept();
        printAnnotationConcepts();
        printBeanMap(context);

        printTitle("USER SERVICE LIFECYCLE TEST (@Bean)");
        System.out.println("Dataflow: Main asks Spring for UserService.");
        System.out.println("Logic: UserService came from AppConfig.userService(), because that method has @Bean.");
        System.out.println("IoC point: Main does NOT call new UserService(). Spring already controls that object.");
        System.out.println("@Bean point: @Bean is used on a METHOD. The method returns the object Spring will manage.");
        UserService userService = context.getBean(UserService.class);
        System.out.println("Main received UserService object: " + objectLabel(userService));
        System.out.println("Now Main calls a business method on that object.");
        userService.createUser("Jawaher");

        printTitle("SINGLETON TEST (DatabaseService)");
        System.out.println("DatabaseService uses @Service.");
        System.out.println("@Service means Spring discovers this class during @ComponentScan.");
        System.out.println("@Service is a specialized @Component for service/business logic classes.");
        System.out.println("Default scope is singleton, so Spring creates ONE object and reuses it.\n");
        System.out.println("IoC point: DatabaseService is not created by Main.");
        System.out.println("           It is created by Spring during container startup, then stored inside the IoC container.\n");

        System.out.println("Main -> Spring: getBean(DatabaseService.class) request #1");
        DatabaseService db1 = context.getBean(DatabaseService.class);
        System.out.println("Spring -> Main: returned " + objectLabel(db1) + " with business ID " + db1.getMyId());

        System.out.println("\nMain -> Spring: getBean(DatabaseService.class) request #2");
        DatabaseService db2 = context.getBean(DatabaseService.class);
        System.out.println("Spring -> Main: returned " + objectLabel(db2) + " with business ID " + db2.getMyId());

        System.out.println("\nMain -> Spring: getBean(DatabaseService.class) request #3");
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
        System.out.println("@Component means Spring discovers it during scanning.");
        System.out.println("@Component is the general annotation: this class is a Spring-managed component.");
        System.out.println("prototype means Spring creates a NEW object every time getBean() is called.\n");
        System.out.println("IoC point: Main does not decide HOW to create the cart.");
        System.out.println("           Main only asks Spring. Spring applies the prototype rule.\n");

        System.out.println("Main -> Spring: getBean(ShoppingCart.class) request #1");
        ShoppingCart cart1 = context.getBean(ShoppingCart.class);
        System.out.println("Spring -> Main: returned " + objectLabel(cart1) + " with cart ID " + cart1.getCartId());

        System.out.println("\nMain -> Spring: getBean(ShoppingCart.class) request #2");
        ShoppingCart cart2 = context.getBean(ShoppingCart.class);
        System.out.println("Spring -> Main: returned " + objectLabel(cart2) + " with cart ID " + cart2.getCartId());

        System.out.println("\nMain -> Spring: getBean(ShoppingCart.class) request #3");
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
        ShoppingCart jawaherCart = context.getBean(ShoppingCart.class);
        System.out.println("Main -> Spring: create a cart for Omar.");
        ShoppingCart omarCart = context.getBean(ShoppingCart.class);

        System.out.println("\nDataflow: item strings move from Main into each customer's own cart object.");
        System.out.println("Main -> Jawaher's cart: addItem(\"iPhone 15\")");
        jawaherCart.addItem("iPhone 15");
        System.out.println("Main -> Jawaher's cart: addItem(\"AirPods\")");
        jawaherCart.addItem("AirPods");
        System.out.println("Main -> Omar's cart: addItem(\"PlayStation 5\")");
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
        context.close();
        System.out.println("Program finished.");
    }

    private static void printTitle(String title) {
        System.out.println("\n==================================================");
        System.out.println(title);
        System.out.println("==================================================");
    }

    private static void printBeanMap(AnnotationConfigApplicationContext context) {
        printTitle("WHAT SPRING KNOWS");
        System.out.println("Spring has these lesson beans registered:");
        System.out.println("- UserService: created by @Bean method in AppConfig");
        System.out.println("- DatabaseService: discovered by @Service and @ComponentScan");
        System.out.println("- ShoppingCart: discovered by @Component and @ComponentScan; scope is prototype");
        System.out.println("\nBean names inside Spring:");
        for (String beanName : context.getBeanDefinitionNames()) {
            if (beanName.contains("userService")
                    || beanName.contains("databaseService")
                    || beanName.contains("shoppingCart")
                    || beanName.contains("appConfig")) {
                System.out.println("- " + beanName);
            }
        }
    }

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

    private static String objectLabel(Object object) {
        return object.getClass().getSimpleName() + "@"
                + Integer.toHexString(System.identityHashCode(object));
    }
}
