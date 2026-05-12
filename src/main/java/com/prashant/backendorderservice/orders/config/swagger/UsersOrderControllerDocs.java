package com.prashant.backendorderservice.orders.config.swagger;

import com.prashant.backendorderservice.orders.dto.request.CreateOrderRequest;
import com.prashant.backendorderservice.orders.dto.request.UpdateOrderStatusRequest;
import com.prashant.backendorderservice.orders.dto.response.OrderResponse;
import com.prashant.backendorderservice.orders.dto.response.UpdateOrderStatusResponse;
import com.prashant.backendorderservice.shared.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UsersOrderControllerDocs {
    // ================= CREATE ORDER =================
    @Operation(summary = "Create a new order")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Order created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid username or password",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                       "timestamp": "2026-03-16T17:46:35.114979100",
                                       "status": 401,
                                       "error": "UNAUTHORIZED",
                                       "message": "Invalid username or password",
                                       "path": "/orders"
                                     }
                                """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid Order Status",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                {
                                  "timestamp": "2026-03-09T10:15:30",
                                  "status": 400,
                                  "error": "INVALID_ENUM_VALUE",
                                  "message": "Valid ENUM VALUE for Status : CREATED, PROCESSING, SHIPPED, COMPLETED, CANCELLED",
                                  "path": "/orders"
                                }
                                """)
                    )
            )
    })
    ResponseEntity<OrderResponse> createOrder(CreateOrderRequest request);

    // ================= UPDATE ORDER STATUS =================

    @Operation(summary = "Update order status")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Order status updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateOrderStatusResponse.class)
                    )
            ),

            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid Order Status",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                {
                                  "timestamp": "2026-03-09T10:15:30",
                                  "status": 400,
                                  "error": "INVALID_ENUM_VALUE",
                                  "message": "Valid Order Status : CREATED, PROCESSING, SHIPPED, COMPLETED, CANCELLED",
                                  "path": "/orders/15/status"
                                }
                                """)
                    )

            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid username or password",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                       "timestamp": "2026-03-16T17:46:35.114979100",
                                       "status": 401,
                                       "error": "UNAUTHORIZED",
                                       "message": "Invalid username or password",
                                       "path": "/orders"
                                     }
                                """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                            {
                              "timestamp": "2026-03-09T10:15:30",
                              "status": 404,
                              "error": "ORDER_NOT_FOUND",
                              "message": "Order not found with id: 15",
                              "path": "/orders/15/status"
                            }
                            """)
                    )
            )
    })
    ResponseEntity<UpdateOrderStatusResponse> updateOrderStatusByUserId(Long id, UpdateOrderStatusRequest request);

    // ================= GET ORDERS=================

    @Operation(summary = "Get All The Orders")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Orders fetched successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class),
                            examples = @ExampleObject(value = """
                            [
                                {
                                    "id": 41,
                                        "customerId": 123,
                                        "description": "iPhone 15 Pro",
                                        "status": "CREATED"
                                },
                                {
                                    "id": 42,
                                        "customerId": 123,
                                        "description": "iPhone 15 Pro",
                                        "status": "CREATED"
                                }
                            ]
                            """)
                    )
            )
    })
    ResponseEntity<List<OrderResponse>> getOrdersByUserId();


    // ================= GET ORDER BY ID=================

    @Operation(summary = "Get order by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Order fetched successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid username or password",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                       "timestamp": "2026-03-16T17:46:35.114979100",
                                       "status": 401,
                                       "error": "UNAUTHORIZED",
                                       "message": "Invalid username or password",
                                       "path": "/orders"
                                     }
                                """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                            {
                              "timestamp": "2026-03-09T10:15:30",
                              "status": 404,
                              "error": "ORDER_NOT_FOUND",
                              "message": "Order not found with id: 15",
                              "path": "/orders/15"
                            }
                            """)
                    )
            )
    })
    ResponseEntity<OrderResponse> getOrderByUserId(Long id);


    // ================= DELETE ORDER =================

    @Operation(summary = "Delete order by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Order deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid username or password",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                       "timestamp": "2026-03-16T17:46:35.114979100",
                                       "status": 401,
                                       "error": "UNAUTHORIZED",
                                       "message": "Invalid username or password",
                                       "path": "/orders"
                                     }
                                """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = """
                            {
                              "timestamp": "2026-03-09T10:15:30",
                              "status": 404,
                              "error": "ORDER_NOT_FOUND",
                              "message": "Order not found with id: 15",
                              "path": "/orders/15"
                            }
                            """)
                    )
            )
    })
    ResponseEntity<Void> deleteOrderByUserId(Long id);

}
