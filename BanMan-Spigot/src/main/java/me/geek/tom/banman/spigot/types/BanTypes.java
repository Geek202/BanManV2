package me.geek.tom.banman.spigot.types;

import com.google.common.collect.Lists;

import java.util.List;

public class BanTypes {

    public List<BanType> types;

    public static BanTypes INSTANCE = new BanTypes();

    private BanTypes() {
        this.types = Lists.newArrayList();
        this.types.add(new AdvertisingType());
        this.types.add(new AutoMineType());
        this.types.add(new ChatType());
        this.types.add(new GriefingType());
        this.types.add(new MinorType());
        this.types.add(new MovementType());
        this.types.add(new PvPType());
        this.types.add(new StaffDisrespectType());
        this.types.add(new XRayType());
    }
}
