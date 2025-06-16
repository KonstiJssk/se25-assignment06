package de.unibayreuth.se.campuscoffee.api.controller;

import de.unibayreuth.se.campuscoffee.api.dtos.ReviewDto;
import de.unibayreuth.se.campuscoffee.api.dtos.UserDto;
import de.unibayreuth.se.campuscoffee.api.mapper.ReviewDtoMapper;
import de.unibayreuth.se.campuscoffee.api.mapper.UserDtoMapper;
import de.unibayreuth.se.campuscoffee.domain.model.Pos;
import de.unibayreuth.se.campuscoffee.domain.model.Review;
import de.unibayreuth.se.campuscoffee.domain.model.User;
import de.unibayreuth.se.campuscoffee.domain.ports.PosService;
import de.unibayreuth.se.campuscoffee.domain.ports.ReviewService;
import de.unibayreuth.se.campuscoffee.domain.ports.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewDtoMapper reviewDtoMapper;
    private final UserDtoMapper userDtoMapper;
    private final PosService posService;
    private final UserService userService;

    @Autowired
    public ReviewController(
            ReviewService reviewService,
            ReviewDtoMapper reviewDtoMapper,
            UserDtoMapper userDtoMapper,
            PosService posService,
            UserService userService
    ) {
        this.reviewService = reviewService;
        this.reviewDtoMapper = reviewDtoMapper;
        this.userDtoMapper = userDtoMapper;
        this.posService = posService;
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAll() {
        List<Review> reviews = reviewService.getAll();
        List<ReviewDto> dtos = reviews.stream()
                .map(reviewDtoMapper::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getById(@PathVariable Long id) {
        Review review = reviewService.getById(id);
        return ResponseEntity.ok(reviewDtoMapper.fromDomain(review));
    }

    @GetMapping("/approved")
    public ResponseEntity<List<ReviewDto>> getApprovedByPosId(@RequestParam("pos_id") Long posId) {
        Pos pos = posService.getById(posId);
        List<Review> approvedReviews = reviewService.getApprovedByPos(pos);
        List<ReviewDto> dtos = approvedReviews.stream()
                .map(reviewDtoMapper::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<ReviewDto> create(@RequestBody @Valid ReviewDto reviewDto) {
        Review review = reviewDtoMapper.toDomain(reviewDto);
        Review created = reviewService.create(review);
        return ResponseEntity.ok(reviewDtoMapper.fromDomain(created));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<ReviewDto> approve(@PathVariable Long id, @RequestBody @Valid UserDto userDto) {
        Review review = reviewService.getById(id);
        User user = userDtoMapper.toDomain(userDto);
        Review approved = reviewService.approve(review, user);
        return ResponseEntity.ok(reviewDtoMapper.fromDomain(approved));
    }
}
