package net.toshimichi.mcbase.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleCommandDetails implements CommandDetails {

    @Getter
    private final String details;
    private final boolean hasPermission;

    public SimpleCommandDetails(String details) {
        this(details, true);
    }

    @Override
    public boolean hasPermission() {
        return hasPermission;
    }
}
