import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/logger-rate-limiter/solution/
 * TODO : implement it correctly again, current solution is not optimal
 * https://leetcode.com/problems/logger-rate-limiter/solution/
 */
class Solution_359 {

    static class Logger {

        Map<String, Integer> messageToTimeMap;

        public Logger() {
            this.messageToTimeMap = new HashMap<>();
        }

        public boolean shouldPrintMessage(int timestamp, String message) {
            if (!this.messageToTimeMap.containsKey(message)) {
                this.messageToTimeMap.put(message, timestamp + 10);
                return true;
            } else {
                int lastTimeStamp = this.messageToTimeMap.get(message);
                if (timestamp >= lastTimeStamp) {
                    this.messageToTimeMap.put(message, timestamp);
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
}


