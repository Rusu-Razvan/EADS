package org.example.homework3.repository;

import org.springframework.stereotype.Repository;

import org.example.homework3.domainModel.Member;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MemberRepository {

    private Map<Integer, Member> members = new HashMap<>();

    public Collection<Member> getMembers(){
        return members.values();
    }

    public void addMember(Member member){
        members.put(member.getId(), member);
    }

    public void removeMember(int id){
        members.remove(id);
    }

    public Member findMemberById(int id){
        return members.get(id);
    }
}
