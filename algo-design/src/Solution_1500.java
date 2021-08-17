import java.util.*;

/**
 * https://leetcode.com/problems/design-a-file-sharing-system/
 */
class Solution_1500 {

    static class FileSharing {

        private final PriorityQueue<Integer> reusableIds = new PriorityQueue<>();
        private final Map<Integer, Set<Integer>> chunkToUserMap = new HashMap<>();
        private final Map<Integer, Set<Integer>> userToChunkMap = new HashMap<>();
        int lastGeneratedId = 0;

        public FileSharing(int m) {
            for (int i = 1; i <= m; i++) {
                chunkToUserMap.put(i, new HashSet<>());
            }
        }

        public int join(List<Integer> ownedChunks) {
            int userid = (reusableIds.isEmpty()) ? ++lastGeneratedId : reusableIds.remove();
            for (int chunk : ownedChunks) {
                chunkToUserMap.get(chunk).add(userid);
            }
            userToChunkMap.put(userid, new HashSet<>(ownedChunks));
            return userid;
        }

        public void leave(int userID) {
            reusableIds.add(userID);
            Set<Integer> userOwnedChunks = userToChunkMap.remove(userID);
            userOwnedChunks.forEach(chunk -> chunkToUserMap.get(chunk).remove(userID));
        }

        public List<Integer> request(int userID, int chunkID) {
            List<Integer> usersWhoOwnChunks = new ArrayList<>(chunkToUserMap.get(chunkID));
            if (!usersWhoOwnChunks.isEmpty()) {
                chunkToUserMap.get(chunkID).add(userID);
                userToChunkMap.get(userID).add(chunkID);
            }
            Collections.sort(usersWhoOwnChunks);
            return usersWhoOwnChunks;
        }
    }
}