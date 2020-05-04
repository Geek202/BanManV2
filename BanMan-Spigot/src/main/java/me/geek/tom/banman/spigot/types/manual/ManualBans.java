package me.geek.tom.banman.spigot.types.manual;

import com.google.common.collect.Lists;
import me.geek.tom.banman.spigot.types.BanType;

import java.util.List;

public class ManualBans {

    public List<BanType> getTypes() {
        return types;
    }

    private List<BanType> types = Lists.newArrayList();

    public static ManualBans INSTANCE = new ManualBans();

    private ManualBans() {
        this.types.add(new SixHour());
        this.types.add(new OneDay());
        this.types.add(new ThreeDay());
        this.types.add(new OneWeek());
        this.types.add(new TwoWeek());
        this.types.add(new OneMonth());
        this.types.add(new SixMonth());
        this.types.add(new BotBan());
        this.types.add(new PermBan());
    }
}
