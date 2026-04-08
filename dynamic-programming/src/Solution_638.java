import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution_638 {


    class Solution {

        List<List<Integer>> special;
        List<Integer> price;
        Map<String, Integer> cache;

        boolean canTakeOffer(int[] needs, List<Integer> offer) {
            for (int i = 0; i < needs.length; i++) {
                if (needs[i] - offer.get(i) < 0) {
                    return false;
                }
            }
            return true;
        }

        boolean isFulFilled(int[] needs) {
            for (int need : needs) {
                if (need > 0) {
                    return false;
                }
            }
            return true;
        }

        void reduceNeedsAsPerOffer(int[] needs, List<Integer> offer) {
            for (int i = 0; i < needs.length; i++) {
                needs[i] -= offer.get(i);
            }
        }

        void increaseNeedsAsPerOffer(int[] needs, List<Integer> offer) {
            for (int i = 0; i < needs.length; i++) {
                needs[i] += offer.get(i);
            }
        }

        // CHANGED → removed 's' from key
        String hashFunction(int[] needs) {
            StringBuilder sb = new StringBuilder();
            for (int need : needs) {
                sb.append(need).append(",");
            }
            return sb.toString();
        }

        public int _shoppingOffers(int[] needs) {

            if (isFulFilled(needs)) {
                return 0;
            }

            String key = hashFunction(needs);

            if (cache.containsKey(key)) {
                return cache.get(key);
            }

            // cost without using any offer
            int cost = payFullPrice(needs);

            // try every offer
            for (int i = 0; i < this.special.size(); i++) {

                List<Integer> offer = this.special.get(i);

                if (canTakeOffer(needs, offer)) {

                    reduceNeedsAsPerOffer(needs, offer);

                    cost = Math.min(
                            cost,
                            offer.get(needs.length) + _shoppingOffers(needs)
                    );

                    increaseNeedsAsPerOffer(needs, offer);
                }
            }

            // IMPORTANT → store result
            cache.put(key, cost);

            return cost;
        }

        private int payFullPrice(int[] needs) {
            int cost = 0;
            for (int i = 0; i < needs.length; i++) {
                cost += needs[i] * this.price.get(i);
            }
            return cost;
        }

        public int shoppingOffers(
                List<Integer> price,
                List<List<Integer>> special,
                List<Integer> needs
        ) {

            this.special = special;
            this.price = price;
            this.cache = new HashMap<>();

            int[] needsArray = new int[needs.size()];

            for (int i = 0; i < needs.size(); i++) {
                needsArray[i] = needs.get(i);
            }

            return _shoppingOffers(needsArray);
        }
    }

    class Solution2 {

        Map<String, Integer> memo = new HashMap<>();

        public int shoppingOffers(List<Integer> price,
                                  List<List<Integer>> special,
                                  List<Integer> needs) {

            return dfs(price, special, needs);
        }

        private int dfs(List<Integer> price,
                        List<List<Integer>> special,
                        List<Integer> needs) {

            String key = needs.toString();

            if (memo.containsKey(key)) {
                return memo.get(key);
            }

            // cost without any offer
            int minCost = 0;
            for (int i = 0; i < needs.size(); i++) {
                minCost += needs.get(i) * price.get(i);
            }

            // try each offer
            for (List<Integer> offer : special) {

                List<Integer> newNeeds = new ArrayList<>();
                boolean valid = true;

                for (int i = 0; i < needs.size(); i++) {

                    if (offer.get(i) > needs.get(i)) {
                        valid = false;
                        break;
                    }

                    newNeeds.add(needs.get(i) - offer.get(i));
                }

                if (valid) {
                    minCost = Math.min(
                            minCost,
                            offer.get(needs.size())
                                    + dfs(price, special, newNeeds)
                    );
                }
            }

            memo.put(key, minCost);

            return minCost;
        }
    }

    class Solution3 {

        Map<Integer, Integer> memo = new HashMap<>();

        public int shoppingOffers(List<Integer> price,
                                  List<List<Integer>> special,
                                  List<Integer> needs) {

            return dfs(price, special, needs);
        }

        private int dfs(List<Integer> price,
                        List<List<Integer>> special,
                        List<Integer> needs) {

            int key = encode(needs);

            if (memo.containsKey(key)) {
                return memo.get(key);
            }

            // cost without any offer
            int minCost = 0;

            for (int i = 0; i < needs.size(); i++) {
                minCost += needs.get(i) * price.get(i);
            }

            // try each offer
            for (List<Integer> offer : special) {

                List<Integer> newNeeds = new ArrayList<>();

                boolean valid = true;

                for (int i = 0; i < needs.size(); i++) {

                    if (offer.get(i) > needs.get(i)) {

                        valid = false;

                        break;
                    }

                    newNeeds.add(
                            needs.get(i) - offer.get(i)
                    );
                }

                if (valid) {

                    minCost = Math.min(
                            minCost,
                            offer.get(needs.size())
                                    + dfs(price, special, newNeeds)
                    );
                }
            }

            memo.put(key, minCost);

            return minCost;
        }

        // 4 bits per item (0–15)
        private int encode(List<Integer> needs) {

            int mask = 0;

            for (int i = 0; i < needs.size(); i++) {

                mask |= (needs.get(i) << (i * 4));

            }

            return mask;
        }
    }

    class Solution4 {

        private Map<Integer, Integer> dp;

        private List<Integer> applyOffer(List<Integer> needs, List<Integer> offer) {
            List<Integer> newNeeds = new ArrayList<>();
            for (int i = 0; i < needs.size(); i++) {
                newNeeds.add(needs.get(i) - offer.get(i));
            }
            return newNeeds;
        }

        private boolean canApplyOffer(List<Integer> needs, List<Integer> offer) {
            for (int i = 0; i < needs.size(); i++) {
                if (needs.get(i) - offer.get(i) < 0) {
                    return false;
                }
            }
            return true;
        }

        // O(N*(2)^S)
        // this does not work because we do not need s to start with, we need to start from zero everytime
        private int _shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs, int s) {
            // base case
            if (isZeroQuantity(needs)) {
                return 0;
            }
            // optimisation
            int hash = getHash(needs);
            if (dp.containsKey(hash)) {
                return dp.get(hash);
            }

            int minCost = withoutOfferCost(needs, price);
            for (int i = s; i < special.size(); i++) {
                List<Integer> offer = special.get(i);
                int skipOffer = _shoppingOffers(price, special, needs, s + 1);
                minCost = Math.min(minCost, skipOffer);
                if (canApplyOffer(needs, offer)) {
                    List<Integer> newNeeds = applyOffer(needs, offer);
                    int takeOffer = _shoppingOffers(price, special, newNeeds, s) + offer.getLast();
                    minCost = Math.min(minCost, takeOffer);
                }
            }
            dp.put(hash, minCost);
            return minCost;
        }

        // O(N)
        private boolean isZeroQuantity(List<Integer> itemQuantity) {
            for (Integer q : itemQuantity) {
                if (q > 0) {
                    return false;
                }
            }
            return true;
        }

        private int getHash(List<Integer> needs) {
            int hash = 0;
            for (int i = 0; i < needs.size(); i++) {
                int v = needs.get(i);
                hash = hash | v << (4 * i);
            }
            return hash;
        }

        // O(N)
        private boolean isOfferCostEffective(List<Integer> offer, List<Integer> prices) {
            int cost = withoutOfferCost(offer, prices);
            return cost >= offer.getLast();
        }

        // O(N)
        private int withoutOfferCost(List<Integer> itemQuantity, List<Integer> prices) {
            int cost = 0;
            for (int i = 0; i < prices.size(); i++) {
                cost = cost + itemQuantity.get(i) * prices.get(i);
            }
            return cost;
        }

        public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
            List<List<Integer>> effectiveOffer = new ArrayList<>();
            // O(S*N)
            // O(S)
            for (List<Integer> offer : special) {
                if (isOfferCostEffective(offer, price)) {
                    effectiveOffer.add(offer);
                }
            }
            return _shoppingOffers(price, effectiveOffer, needs, 0);
        }
    }
}
