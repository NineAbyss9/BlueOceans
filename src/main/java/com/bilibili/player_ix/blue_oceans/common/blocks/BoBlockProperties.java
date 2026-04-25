
package com.bilibili.player_ix.blue_oceans.common.blocks;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public class BoBlockProperties {
    public static final IntegerProperty BITES = IntegerProperty.create("bites", 0, 10);
    public static final IntegerProperty GROWTH_AGE = IntegerProperty.create("age", 0, 600);
    public static final BooleanProperty COVERED = BooleanProperty.create("covered");
    public static final IntegerProperty CAPACITY = IntegerProperty.create("capacity",
            0, 8);
    public static final BooleanProperty BURNING = BooleanProperty.create("burning");
    public static final BooleanProperty TOP = BooleanProperty.create("top");
    public static final BooleanProperty END = BooleanProperty.create("end");
    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");

    public static class LongProperty extends Property<Long> {
        private final ImmutableSet<Long> values;
        private final long min;
        private final long max;
        public LongProperty(String pName,  long pMin, long pMax)
        {
            super(pName, Long.class);
            if (pMax <= pMin) {
                throw new IllegalArgumentException("Max value of " + pName + " must be greater than min (" + pMin + ")");
            } else {
                this.min = pMin;
                this.max = pMax;
                Set<Long> set = Sets.<Long>newHashSet();
                for (long i  = pMin; i <= pMax; ++i) {
                    set.add(i);
                }
                this.values = ImmutableSet.copyOf(set);
            }
        }

        public Collection<Long> getPossibleValues()
        {
            return values;
        }

        public String getName(Long p_61696_)
        {
            return p_61696_.toString();
        }

        public Optional<Long> getValue(String pValue)
        {
            try {
                long integer = Long.parseLong(pValue);
                return integer >= this.min && integer <= this.max ? Optional.of(integer) : Optional.empty();
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }

        public LongProperty create(String name, long min, long max) {
            return new LongProperty(name, min, max);
        }
    }
}
