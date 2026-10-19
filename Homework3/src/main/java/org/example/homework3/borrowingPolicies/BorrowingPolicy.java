package org.example.homework3.borrowingPolicies;

import org.example.homework3.domainModel.Member;

public interface BorrowingPolicy {

    public boolean canBorrow(Member member);

}
