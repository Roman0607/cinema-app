package cinema.util;

import cinema.model.CinemaHall;
import cinema.model.Movie;
import cinema.model.MovieSession;
import cinema.model.Role;
import cinema.model.User;
import cinema.service.AuthenticationService;
import cinema.service.CinemaHallService;
import cinema.service.MovieService;
import cinema.service.MovieSessionService;
import cinema.service.RoleService;
import cinema.service.UserService;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final RoleService roleService;
    private final UserService userService;
    private final MovieService movieService;
    private final CinemaHallService cinemaHallService;
    private final MovieSessionService movieSessionService;
    private final AuthenticationService authenticationService;

    public DataInitializer(RoleService roleService,
                           UserService userService,
                           MovieService movieService,
                           CinemaHallService cinemaHallService,
                           MovieSessionService movieSessionService,
                           AuthenticationService authenticationService) {
        this.roleService = roleService;
        this.userService = userService;
        this.movieService = movieService;
        this.cinemaHallService = cinemaHallService;
        this.movieSessionService = movieSessionService;
        this.authenticationService = authenticationService;
    }

    @PostConstruct
    public void inject() {
        roleService.add(new Role(Role.RoleName.ROLE_ADMIN));
        roleService.add(new Role(Role.RoleName.ROLE_USER));

        User bob = new User();
        authenticationService.register("bob@gmail.com", "1234");

        User alice = new User();
        alice.setEmail("alice@gmail.com");
        alice.setPassword("12345");
        alice.setRoles(Stream.of(roleService.getRoleByName(Role.RoleName.ROLE_ADMIN))
                .collect(Collectors.toSet()));
        userService.add(alice);

        Movie fastAndFurious = new Movie();
        fastAndFurious.setTitle("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(100);
        firstCinemaHall.setDescription("first hall with capacity 100");
        CinemaHall secondCinemaHall = new CinemaHall();
        secondCinemaHall.setCapacity(200);
        secondCinemaHall.setDescription("second hall with capacity 200");
        cinemaHallService.add(firstCinemaHall);
        cinemaHallService.add(secondCinemaHall);

        MovieSession tomorrowMovieSession = new MovieSession();
        tomorrowMovieSession.setCinemaHall(firstCinemaHall);
        tomorrowMovieSession.setMovie(fastAndFurious);
        tomorrowMovieSession.setShowTime(LocalDateTime.now().plusDays(1L));
        MovieSession yesterdayMovieSession = new MovieSession();
        yesterdayMovieSession.setCinemaHall(firstCinemaHall);
        yesterdayMovieSession.setMovie(fastAndFurious);
        yesterdayMovieSession.setShowTime(LocalDateTime.now().minusDays(1L));

        movieSessionService.add(tomorrowMovieSession);
        movieSessionService.add(yesterdayMovieSession);
    }
}
