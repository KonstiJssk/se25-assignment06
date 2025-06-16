package de.unibayreuth.se.campuscoffee.domain.tests;

import de.unibayreuth.se.campuscoffee.domain.model.*;
import de.unibayreuth.se.campuscoffee.domain.ports.PosService;
import de.unibayreuth.se.campuscoffee.domain.ports.ReviewService;
import de.unibayreuth.se.campuscoffee.domain.ports.UserService;
import org.apache.commons.lang3.SerializationUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TestFixtures {
    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2025, 6, 3, 12, 0, 0);

    private static final List<User> USER_LIST = List.of(
            new User(1L, DATE_TIME, DATE_TIME, "jane_doe", "jane.doe@uni-bayreuth.de", "Jane", "Doe"),
            new User(2L, DATE_TIME, DATE_TIME,"maxmustermann", "max.mustermann@campus.de", "Max", "Mustermann"),
            new User(3L, DATE_TIME, DATE_TIME,"student2023", "student2023@study.org", "Student", "Example")
    );

    private static final List<Pos> POS_LIST = List.of(
            new Pos(1L, DATE_TIME, DATE_TIME, "CrazySheep (RW-I)", "Description 1", PosType.CAFE, CampusType.MAIN, "Andreas-Maisel-Weg", "2", 95445, "Bayreuth"),
            new Pos(1L, DATE_TIME, DATE_TIME, "Cafeteria (Mensa)", "Description 1", PosType.CAFE, CampusType.MAIN, "Universitätsstraße", "30", 95447, "Bayreuth"),
            new Pos(1L, DATE_TIME, DATE_TIME, "Lidl (Nürnberger Str.)", "Description 1", PosType.VENDING_MACHINE, CampusType.ZAPF, "Nürnberger Str.", "3a", 95448, "Bayreuth")
    );

    private static final List<Review> REVIEW_LIST = List.of(
            // TODO: create suitable review test fixtures
            Review.builder()
                    .id(1L)
                    .createdAt(DATE_TIME)
                    .pos(POS_LIST.get(0))          // CrazySheep
                    .author(USER_LIST.get(0))      // Jane Doe
                    .review("Gemütlicher Ort mit gutem Kaffee.")
                    .approvalCount(2)              // approved
                    .build(),
            Review.builder()
                    .id(2L)
                    .createdAt(DATE_TIME)
                    .pos(POS_LIST.get(1))          // Mensa
                    .author(USER_LIST.get(1))      // Max Mustermann
                    .review("Ganz ok, aber lange Warteschlangen.")
                    .approvalCount(1)              // not approved
                    .build(),
            Review.builder()
                    .id(3L)
                    .createdAt(DATE_TIME)
                    .pos(POS_LIST.get(2))          // Lidl Automat
                    .author(USER_LIST.get(2))      // Student Example
                    .review("Perfekt für einen schnellen Snack.")
                    .approvalCount(0)              // not approved
                    .build()
    );

    public static List<User> getUserList() {
        return USER_LIST.stream()
                .map(SerializationUtils::clone)
                .toList();
    }

    public static List<User> getUserListForInsertion() {
        return getUserList().stream()
                .map(user -> user.toBuilder().id(null).createdAt(null).updatedAt(null).build())
                .toList();
    }

    public static List<Pos> getPosList() {
        return POS_LIST.stream()
                .map(SerializationUtils::clone) // prevent issues when tests modify the fixture objects
                .toList();
    }

    public static List<Pos> getPosListForInsertion() {
        return getPosList().stream()
                .map(user -> user.toBuilder().id(null).createdAt(null).updatedAt(null).build())
                .toList();
    }

    public static List<Review> getReviewList() {
        return REVIEW_LIST.stream()
                .map(SerializationUtils::clone) // prevent issues when tests modify the fixture objects
                .toList();
    }

    public static List<User> createUsers(UserService userService) {
        return getUserListForInsertion().stream()
                .map(userService::upsert)
                .collect(Collectors.toList());
    }

    public static List<Pos> createPos(PosService posService) {
        return getPosListForInsertion().stream()
                .map(posService::upsert)
                .collect(Collectors.toList());
    }

    public static List<Review> createReviews(ReviewService reviewService, PosService posService, UserService userService) {
        List<Pos> posList = posService.getAll();
        List<User> userList = userService.getAll();
        // TODO: create suitable review test fixtures (linking them to the POS and user test fixtures)

        Review review1 = Review.builder()
                .pos(posList.get(0))
                .author(userList.get(0))
                .review("Guter Kaffee und nettes Personal.")
                .approvalCount(2)
                .build();

        Review review2 = Review.builder()
                .pos(posList.get(1))
                .author(userList.get(1))
                .review("Lange Schlangen, aber gut.")
                .approvalCount(1)
                .build();

        Review review3 = Review.builder()
                .pos(posList.get(2))
                .author(userList.get(2))
                .review("Schnell, aber oft leer.")
                .approvalCount(0)
                .build();

        reviewService.create(review1);
        reviewService.create(review2);
        reviewService.create(review3);


        return reviewService.getAll();
    }
}
