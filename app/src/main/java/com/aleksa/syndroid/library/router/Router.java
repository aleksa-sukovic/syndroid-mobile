package com.aleksa.syndroid.library.router;
import android.content.Context;

import com.aleksa.syndroid.library.controllers.BaseController;
import com.aleksa.syndroid.library.exceptions.HandlerNotFoundException;
import com.aleksa.syndroid.library.exceptions.RouteNotFoundException;
import com.aleksa.syndroid.library.exceptions.ValidationException;
import com.aleksa.syndroid.library.router.request.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Router
{
    private List<Route> routes;
    private Context context;
    private static Map<Long, ResponseCallback> responseListeners;

    static {
        responseListeners = new HashMap<>();
    }

    public Router(Context context, List<Route> routes)
    {
        this.context = context;
        this.routes = routes;
    }

    public Request sendRequest(Request request, ResponseCallback listener)
    {
        if (request.expectsResponse() && listener != null) {
            responseListeners.put(request.getID(), listener);
        }

        return request;
    }

    public String handle(String data) throws RouteNotFoundException, HandlerNotFoundException, ValidationException
    {
        Request request = new Request(new Route(data));

        if (request.typeResponse()) {
            return this.handleTypeResponse(request);
        }

        if (request.typeRequest()) {
            return this.handleTypeRequest(request);
        }

        return null;
    }

    private String handleTypeResponse(Request request)
    {
        ResponseCallback listener = responseListeners.get(request.getID());

        if (listener != null) {
            listener.consume(request);
            responseListeners.remove(request.getID());
        }

        return null;
    }

    private String handleTypeRequest(Request request) throws RouteNotFoundException, HandlerNotFoundException, ValidationException
    {
        Route foundRoute = this.findRoute(request.getRoute());
        if (foundRoute == null) {
            throw new RouteNotFoundException(request);
        }

        request.setRoute(foundRoute);
        BaseController controller = request.getRoute().getController();
        String response = controller.handle(context, request);

        return request.expectsResponse() ? response : null;
    }

    private Route findRoute(Route route)
    {
        for (int i = 0; i < routes.size(); i++) {
            Route availableRoute = routes.get(i);

            if (availableRoute.match(route)) {
                return new Route(route.toString(), availableRoute.getControllerName(), availableRoute.getHandler());
            }
        }

        return null;
    }

    public void stop()
    {
        context = null;
    }

    public interface ResponseCallback
    {
        void consume(Request response);
    }
}
