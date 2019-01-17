package com.aleksa.syndroid.library.managers;

import android.content.Context;

import com.aleksa.syndroid.library.controllers.BaseController;
import com.aleksa.syndroid.library.controllers.exceptions.HandlerNotFoundException;
import com.aleksa.syndroid.library.exceptions.InvalidRouteException;
import com.aleksa.syndroid.library.exceptions.RouteNotFoundException;
import com.aleksa.syndroid.library.router.Route;
import com.aleksa.syndroid.library.router.Router;
import com.aleksa.syndroid.library.router.request.IncomingRequest;
import com.aleksa.syndroid.library.validators.exceptions.ValidationException;

import java.util.List;

public class RequestManager implements Manager<String, String>
{

    private Router router;
    private Context context;

    RequestManager(Context context, List<Route> availableRoutes)
    {
        this.router = new Router(availableRoutes);
        this.context = context;
    }

    @Override
    public String manage(String incomingMessage) throws InvalidRouteException, RouteNotFoundException, HandlerNotFoundException, ValidationException
    {
        IncomingRequest request = router.handle(incomingMessage);

        BaseController controller = request.getRoute().getController();

        return controller.handle(this.context, request);
    }

    public void cleanUp()
    {
        this.context = null;
    }
}
