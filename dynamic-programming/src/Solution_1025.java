class Solution_1025 {

    /**
     * If the player has even value then player can always return other player an odd value, by choosing x = 1; So , if value was 4 then player could choose x=1 and return 3.
     * If a player get odd value than player has no choice but to return even value.
     * Proof: lets say player with odd value can return odd value then n(odd) - x  = v(odd) => x = n(odd) - v(odd) => x is even
     * but there does not exists any even factors of a odd number. hence disproved.
     * and if player gets 2 than player wins.
     * So by induction, 3 would loose as it has to return 2, 4 would win as it can force the other player to get 3 and so on.
     * So if player get even number, player win because the loop of even , odd, even go on until a player get 2
     */
    public boolean divisorGame(int n) {
        return n % 2 == 0;
    }
}