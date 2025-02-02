package com.Da_Technomancer.essentials.blocks;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.core.Direction;

public class ESProperties{

	public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.values());
	public static final DirectionProperty HORIZ_FACING = DirectionProperty.create("horiz_facing", Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH);
	//0: Empty, 1: water, 2: lava, 3: coal, 4: glowstone, 5: reserved, 6: soul sand
	public static final IntegerProperty BRAZIER_CONTENTS = IntegerProperty.create("br_cont", 0, 6);
	public static final BooleanProperty REDSTONE_BOOL = BooleanProperty.create("redstone_bool");
	public static final IntegerProperty REDSTONE = IntegerProperty.create("redstone", 0, 15);
	public static final BooleanProperty EXTENDED = BooleanProperty.create("extended");
	public static final BooleanProperty SHIFTING = BooleanProperty.create("shifting");
	//0: No head, 1: Head in positive direction, 2: Head in negative direction
	public static final IntegerProperty HEAD = IntegerProperty.create("head", 0, 2);
	public static final EnumProperty<Direction.Axis> AXIS = EnumProperty.create("axis", Direction.Axis.class);
	//Connections for 4 sides. v & 1 -> north; v & 2 -> south; v & 4 -> west; v & 8 -> east
	public static final IntegerProperty CONNECTIONS = IntegerProperty.create("connections", 0, 15);
	public static final EnumProperty<DyeColor> COLOR = EnumProperty.create("color", DyeColor.class);
}
