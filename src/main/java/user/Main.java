package user;



import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:users_table");
        jdbi.installPlugin(new SqlObjectPlugin());

        try (Handle handle = jdbi.open()) {

            UserDao dao = handle.attach(UserDao.class);
            dao.createTable();

            User user = User.builder().username("lajos12").password("alma123").name("Nagy Lajos").email("alma@gmail.com").gender(User.Gender.MALE).dob(LocalDate.parse("1990-10-11")).enabled(true).build();
            User user2 = User.builder().username("zita91").password("körte123").name("Kovács Zita").email("zita@gmail.com").gender(User.Gender.FEMALE).dob(LocalDate.parse("1985-12-12")).enabled(true).build();
            User user3 = User.builder().username("arpad20").password("ananász13").name("Nagy Árpád").email("arpi@gmail.com").gender(User.Gender.MALE).dob(LocalDate.parse("2000-12-12")).enabled(true).build();

            dao.insert(user);
            dao.insert(user2);
            dao.insert(user3);

            System.out.println("Database right now:");
            dao.list().stream().forEach(System.out::println);

            System.out.println("Find a user by id(3):");
            dao.findById((long)3).stream().forEach(System.out::println);

            System.out.println("Find a user by username(zita91):");
            dao.findByUsername("zita91").stream().forEach(System.out::println);

            System.out.println("Delete user2.");
            dao.delete(user2);

            System.out.println("Database after deleteing user2: ");
            dao.list().stream().forEach(System.out::println);

        }
    }
}
