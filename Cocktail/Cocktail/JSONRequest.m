//
//  JSONRequest.m
//  DJVU
//
//  Created by Otto Sipe on 11/6/13.
//  Copyright (c) 2013 DJVU. All rights reserved.
//

#import "JSONRequest.h"

@implementation JSONRequest

- (id)initWithBase:(NSString*)base
{
    if (self = [super init]) {
        _baseAPIendpoint = base;
        _queueName = @"com.cocktail.api.search.queue";
    }
    return self;
}

- (void)performRequestWithUri:(NSString *)requestURI
                       method:(NSString *)method
                         body:(NSDictionary *)body
                   completion:(void (^)(id, NSError *))completion
{
    if (![method isEqualToString:@"POST"] &&
        ![method isEqualToString:@"GET"] &&
        ![method isEqualToString:@"DELETE"] &&
        ![method isEqualToString:@"PUT"]) {
        NSLog(@"Cannot send HTTP request with method %@", method);
        return;
    }
    
    // Generate the URL
    NSString *requestUrlRaw = [NSString stringWithFormat:@"%@%@", self.baseAPIendpoint, requestURI];
    // Make Clean URL string
    NSString *requestUrl = [requestUrlRaw stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
    
    NSLog(@"Request URL: %@", requestUrl);
    
    // Create the connection
    NSMutableURLRequest *request =
        [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString:requestUrl]
                                     cachePolicy:NSURLRequestUseProtocolCachePolicy
                                 timeoutInterval:60.0];
    [request setHTTPMethod:method];
    
    // Add the body in JSON format.
    if(body) {
        NSError* jsonError;
        [request setValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
        NSData *jsonData = [NSJSONSerialization dataWithJSONObject:body options:0 error:&jsonError];
        [request setHTTPBody:jsonData];
        
        if (jsonError) {
            NSLog(@"Cannot serialize JSON body!");
            return;
        }
    }
    
    // Make an NSOperationQueue
    NSOperationQueue *queue = [[NSOperationQueue alloc] init];
    [queue setName:self.queueName];
        
    // Send an asyncronous request on the queue
    [NSURLConnection sendAsynchronousRequest:request
                                       queue:queue
                           completionHandler:^(NSURLResponse *response, NSData *data, NSError *error) {
        // If there was an error getting the data
        if (error) {
            dispatch_async(dispatch_get_main_queue(), ^(void) {
                completion(nil, error);
            });
            return;
        }
        
        // Decode the data
        NSError *jsonError;
        id responseObject = [NSJSONSerialization JSONObjectWithData:data options:0 error:&jsonError];
        
        // If there was an error decoding the JSON
        if (jsonError) {
            dispatch_async(dispatch_get_main_queue(), ^(void) {
                completion(nil, nil);
            });
            return;
        }
        
        // All looks fine, lets call the completion block with the response data
        dispatch_async(dispatch_get_main_queue(), ^(void) {
            completion(responseObject, nil);
        });
    }];
}

@end
