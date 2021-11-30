package dev.lightdream.RealmsCore.dto;

import dev.lightdream.api.dto.PluginLocation;
import dev.lightdream.libs.fasterxml.annotation.JsonIgnore;
import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public class Coords {

    public int x;
    public int z;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coords coords = (Coords) o;
        return x == coords.x && z == coords.z;
    }

    @JsonIgnore
    public PluginLocation getLocation(String world) {
        return new PluginLocation(world, x, 0, z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, z);
    }

    @Override
    public String toString() {
        return "Coords{" +
                "x=" + x +
                ", y=" + z +
                '}';
    }
}

