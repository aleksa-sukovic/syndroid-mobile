package com.aleksa.syndroid.library.database.promises;

import java.util.List;

public interface PromiseResult<Entity>
{
    void itemResult(Entity data);
    void listResult(List<Entity> data);
}
