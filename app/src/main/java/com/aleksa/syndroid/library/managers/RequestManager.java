package com.aleksa.syndroid.library.managers;

import android.util.Log;

import com.aleksa.syndroid.library.application.Application;
import com.aleksa.syndroid.library.bootstrap.Bootstrapper;
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

    RequestManager(List<Route> availableRoutes)
    {
        if (Application.getInstance() == null) {
            return;
        }

        this.router = new Router(availableRoutes);
    }

    @Override
    public String manage(String incomingMessage) throws InvalidRouteException, RouteNotFoundException, HandlerNotFoundException, ValidationException
    {
        IncomingRequest request = router.handle(incomingMessage);

        BaseController controller = request.getRoute().getController();

        return controller.handle(request);
    }

}
