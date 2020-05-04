package me.geek.tom.banman.spigot.types;

import java.time.temporal.TemporalAmount;
import java.util.HashMap;
import java.util.Map;

public abstract class BanTypeImpl extends BanType {
    private Map<Integer, TemporalAmount> countDurationMap = new HashMap<>();

    @Override
    public void setSentence(int count, TemporalAmount duration) {
        this.countDurationMap.put(count, duration);
    }

    @Override
    public TemporalAmount getDuration(int count) throws IllegalArgumentException {
        if (!this.countDurationMap.keySet().contains(count)) {
            int maxCount = 0;
            for (int cnt : this.countDurationMap.keySet()) {
                maxCount = Math.max(maxCount, cnt);
            }
            return this.countDurationMap.get(maxCount);
        }
        return this.countDurationMap.get(count);
    }
}
