package net.thomilist.dimensionalinventories.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;

import java.util.UUID;

// Intended to hold data during data migrations: load the old format to this, save this to the new format
public class DummyServerPlayerEntity
    extends ServerPlayer
{
    private static final String DUMMY_NAME = "TempPlayer";

    private DummyServerPlayerEntity( final ServerLevel world, final GameProfile profile )
    {
        super( world.getServer(), world, profile, ClientInformation.createDefault() );
    }

    private DummyServerPlayerEntity( final MinecraftServer server, final GameProfile profile )
    {
        super( server, server.overworld(), profile, ClientInformation.createDefault() );
    }

    public DummyServerPlayerEntity( final ServerLevel world, final UUID uuid )
    {
        this( world, new GameProfile( uuid, DummyServerPlayerEntity.DUMMY_NAME ) );
    }

    public DummyServerPlayerEntity( final MinecraftServer server, final UUID uuid )
    {
        this( server, new GameProfile( uuid, DummyServerPlayerEntity.DUMMY_NAME ) );
    }

    public DummyServerPlayerEntity( final ServerLevel world, final String uuid )
    {
        this( world, UUID.fromString( uuid ) );
    }

    public DummyServerPlayerEntity( final MinecraftServer server, final String uuid )
    {
        this( server, UUID.fromString( uuid ) );
    }

    public DummyServerPlayerEntity( final MinecraftServer server )
    {
        this( server, UUID.randomUUID() );
    }

    public DummyServerPlayerEntity( final ServerLevel world )
    {
        this( world, UUID.randomUUID() );
    }
}
