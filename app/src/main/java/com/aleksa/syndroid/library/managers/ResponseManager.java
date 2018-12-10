package com.aleksa.syndroid.library.managers;

import com.aleksa.syndroid.library.router.Route;
import com.aleksa.syndroid.library.router.RouteMatcher;
import com.aleksa.syndroid.library.router.RouteParser;
import com.aleksa.syndroid.library.router.request.OutgoingRequest;
import com.aleksa.syndroid.library.router.request.Request;
import com.aleksa.syndroid.library.router.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResponseManager implements Manager<String, String>
{
    private List<OutgoingRequest> queue;
    private RouteParser routeParser;
    private RouteMatcher routeMatcher;

    ResponseManager()
    {
        routeParser = new RouteParser();
        routeMatcher = new RouteMatcher(new ArrayList<>());
        queue = new ArrayList<>();
    }

    @Override
    public String manage(String message)
    {
        Map<String, String> params = routeParser.parseParams(new Route(), message);
        OutgoingRequest request = findQueuedRequest(params.get("request_id"));

        if (request == null || !isValidResponse(request, message)) {
            return null;
        }

        removeFromQueue(request);
        Response response = new Response(request.getRoute(), routeParser.parseParams(request.getRoute(), message));
        request.fireResponseCallback(response);

        return null;
    }

    void expectResponseFor(OutgoingRequest request)
    {
        queue.add(request);
    }

    private boolean isValidResponse(OutgoingRequest request, String message)
    {
        return request.getRoute().isExceptionRoute() || routeMatcher.matchWith(message, request.getRoute());
    }

    private void removeFromQueue(Request request)
    {
        for (int i = 0; i < queue.size(); i++) {
            if (queue.get(i).getId() == request.getId()) {
                queue.remove(i);
            }
        }
    }

    private OutgoingRequest findQueuedRequest(String id)
    {
        for (OutgoingRequest request : queue) {
            String requestId = Long.toString(request.getId());

            if (!requestId.isEmpty() && requestId.equals(id)) {
                return request;
            }
        }

        return null;
    }
}
