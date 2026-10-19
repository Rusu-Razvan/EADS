package org.example.homework3.borrowingPolicies;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "library.policy")
public class LibraryPolicyConfiguration {

    private String type = "fixed"; //Valoare implicita
    private Limit limit = new Limit();

    //Clasa interioara pentru a mapa sectiunea "limit:"
    public static class Limit {
        private int fixed = 3;
        private int student = 4;
        private int regular = 3;
        private int premium = 5;

        public int getFixed() { return fixed; }
        public void setFixed(int fixed) { this.fixed = fixed; }

        public int getStudent() { return student; }
        public void setStudent(int student) { this.student = student; }

        public int getRegular() { return regular; }
        public void setRegular(int regular) { this.regular = regular; }

        public int getPremium() { return premium; }
        public void setPremium(int premium) { this.premium = premium; }
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Limit getLimit() { return limit; }
    public void setLimit(Limit limit) { this.limit = limit; }
}