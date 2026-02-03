package io.gihub.andret22.simple_api.core.domain;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

public class Team {

    public UUID id;

    public String name;

    public String created_at;

    public String updated_at;

    public List<Person> people = new ArrayList<>();
}
