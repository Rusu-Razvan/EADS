package org.example.homework3.borrowingPolicies;

import org.example.homework3.domainModel.Member;
import org.springframework.stereotype.Component;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Component
@ConditionalOnProperty(name = "library.policy.type", havingValue = "role-based")
public class RoleBasedBorrowingPolicy implements BorrowingPolicy{

    private final LibraryPolicyConfiguration configuration;

    public RoleBasedBorrowingPolicy(LibraryPolicyConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public boolean canBorrow(Member member){

        int maxBooks = switch(member.getRole()) {

            case STUDENT -> configuration.getLimit().getStudent();
            case REGULAR -> configuration.getLimit().getRegular();
            case PREMIUM -> configuration.getLimit().getPremium();
            default -> 0;

        };

        return member.getBorrowedBooks().size() < maxBooks;
    }

}
