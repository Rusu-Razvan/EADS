package org.example.homework3.borrowingPolicies;

import org.example.homework3.domainModel.Member;
import org.example.homework3.domainModel.MemberRole;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "library.policy.type", havingValue = "fixed")
public class FixedBorrowingPolicy implements BorrowingPolicy{

    private final LibraryPolicyConfiguration configuration;

    public FixedBorrowingPolicy(LibraryPolicyConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public boolean canBorrow(Member member){

        return member.getBorrowedBooks().size() < configuration.getLimit().getFixed();

    }

}
