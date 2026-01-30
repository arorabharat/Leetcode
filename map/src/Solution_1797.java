import java.util.*;

public class Solution_1797 {

    class AuthenticationManager1 {

        private final Map<String, Integer> tokenStore = new HashMap<>();
        private final int ttl;
        private final TreeMap<Integer, Integer> ttlToCount = new TreeMap<>();

        public AuthenticationManager1(int timeToLive) {
            this.ttl = timeToLive;
        }

        public void generate(String tokenId, int currentTime) {
            tokenStore.computeIfAbsent(tokenId, k -> currentTime + this.ttl);
        }

        public void renew(String tokenId, int currentTime) {
            if (tokenStore.containsKey(tokenId) && tokenStore.get(tokenId) > currentTime && currentTime > tokenStore.get(tokenId) - this.ttl) {
                int newTTL = currentTime + this.ttl;
                tokenStore.compute(tokenId, (k, v) -> newTTL);
                ttlToCount.put(newTTL, ttlToCount.getOrDefault(newTTL, 0) + 1);
            }
        }

        public int countUnexpiredTokens(int currentTime) {
            int count = 0;
            for (Integer v : ttlToCount.tailMap(currentTime, false).values()) {
                count += v;
            }
            return count;
        }
    }

    // this did not work
    class AuthenticationManager2 {

        class Token implements Comparable<Token> {

            String id;
            Integer expiry;

            public Token(String id, Integer expiry) {
                this.id = id;
                this.expiry = expiry;
            }

            @Override
            public int compareTo(Token o) {
                if (Objects.equals(this.expiry, o.expiry)) {
                    return id.compareTo(o.id);
                } else {
                    return Integer.compare(this.expiry, o.expiry);
                }
            }
        }

        private final Map<String, Integer> tokenStore = new HashMap<>();
        private final int ttl;

        private final PriorityQueue<Token> q = new PriorityQueue<>();

        public AuthenticationManager2(int timeToLive) {
            this.ttl = timeToLive;
        }

        public void generate(String tokenId, int currentTime) {
            int expiry = currentTime + this.ttl;
            tokenStore.put(tokenId, expiry);
            q.add(new Token(tokenId, expiry));
        }

        public void renew(String tokenId, int currentTime) {
            Integer expiry = tokenStore.get(tokenId);
            if (expiry != null && expiry > currentTime) {
                int newExpiry = currentTime + this.ttl;
                tokenStore.put(tokenId, newExpiry);
                q.add(new Token(tokenId, newExpiry));
            }
        }

        public int countUnexpiredTokens(int currentTime) {
            while (!q.isEmpty() && q.peek().expiry < currentTime) {
                q.remove();
            }
            return q.size();
        }
    }

    // this worked like charm
    class AuthenticationManager3 {

        private final Map<String, Integer> tokenStore = new HashMap<>();
        private final TreeMap<Integer, Set<String>> expiryMap = new TreeMap<>();
        private final int ttl;


        public AuthenticationManager3(int timeToLive) {
            this.ttl = timeToLive;
        }

        public void generate(String tokenId, int currentTime) {
            addToken(tokenId, currentTime);
        }

        public void renew(String tokenId, int currentTime) {
            Integer expiry = tokenStore.get(tokenId);
            if (expiry != null && expiry > currentTime) {
                cleanUpToken(tokenId, expiry);
                addToken(tokenId, currentTime);
            }
        }

        private void cleanUpToken(String tokenId, Integer expiry) {
            tokenStore.remove(tokenId);
            expiryMap.get(expiry).remove(tokenId);
            if(expiryMap.get(expiry).isEmpty()) {
                expiryMap.remove(expiry);
            }
        }

        private void addToken(String tokenId, int currentTime) {
            int expiry = currentTime + this.ttl;
            tokenStore.put(tokenId, expiry);
            expiryMap.computeIfAbsent(expiry, k-> new HashSet<>()).add(tokenId);
        }

        public int countUnexpiredTokens(int currentTime) {
            SortedMap<Integer, Set<String>> expiredMap = expiryMap.headMap(currentTime + 1);
            for (Set<String> expiredTokenSet: expiredMap.values()) {
                expiredTokenSet.forEach(tokenStore::remove);
            }
            expiredMap.clear();
            return tokenStore.size();
        }
    }

/**
 * Your AuthenticationManager object will be instantiated and called as such:
 * AuthenticationManager obj = new AuthenticationManager(timeToLive);
 * obj.generate(tokenId,currentTime);
 * obj.renew(tokenId,currentTime);
 * int param_3 = obj.countUnexpiredTokens(currentTime);
 */
}
