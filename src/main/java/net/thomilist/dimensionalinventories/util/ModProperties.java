package net.thomilist.dimensionalinventories.util;

import java.util.ArrayList;
import java.util.List;

public class ModProperties
{
    private final String id;
    private final List<String> authors = new ArrayList<>();
    private String namePretty = "<unknown mod name>";
    private String namePascal = "<unknown mod name>";
    private String version = "<unknown mod version>";
    private String description = "<unknown mod description>";

    public ModProperties( final String modId )
    {
        this.id = modId;

        this.version = "2.2.1+1.21.1-neoforge";
        this.namePretty = "Dimensional Inventories";
        this.namePascal = StringHelper.toPascalCase( this.namePretty );
        this.description = "Keep separate inventories across pools of dimensions.";
        this.authors.add( "Thomilist" );
    }

    public String version()
    {
        return this.version;
    }

    public String id()
    {
        return this.id;
    }

    public String namePretty()
    {
        return this.namePretty;
    }

    public String namePascal()
    {
        return this.namePascal;
    }

    public String description()
    {
        return this.description;
    }

    public List<String> authors()
    {
        return this.authors;
    }

    public String authorsPretty()
    {
        if ( this.authors.isEmpty() )
        {
            return "<no authors found>";
        }
        else
        {
            return StringHelper.joinLastDifferent( ", ", " & ", this.authors );
        }
    }
}
