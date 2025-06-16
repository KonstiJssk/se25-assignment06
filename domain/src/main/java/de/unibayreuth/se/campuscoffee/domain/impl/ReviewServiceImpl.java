package de.unibayreuth.se.campuscoffee.domain.impl;

import de.unibayreuth.se.campuscoffee.domain.exceptions.PosNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.exceptions.ReviewNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.exceptions.UserNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.model.Pos;
import de.unibayreuth.se.campuscoffee.domain.model.Review;
import de.unibayreuth.se.campuscoffee.domain.model.User;
import de.unibayreuth.se.campuscoffee.domain.ports.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Override
    public void clear() {

    }

    @Override
    public List<Review> getAll() {
        return List.of();
    }

    @Override
    public Review getById(Long id) throws ReviewNotFoundException {
        return null;
    }

    @Override
    public List<Review> getApprovedByPos(Pos pos) throws PosNotFoundException {
        return List.of();
    }

    @Override
    public Review create(Review review) throws PosNotFoundException, UserNotFoundException {
        return null;
    }

    @Override
    public Review approve(Review review, User user) throws ReviewNotFoundException, UserNotFoundException, IllegalArgumentException {
        return null;
    }
}
