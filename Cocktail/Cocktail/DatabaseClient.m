//
//  DatabaseClient.m
//  DJVU
//
//  Created by William Joshua Billingham on 11/6/13.
//  Copyright (c) 2013 DJVU. All rights reserved.
//

#import "DatabaseClient.h"
#import "JSONRequest.h"

@implementation DatabaseClient

NSString *base = @"http://cocktail.aws.af.cm/";

+ (void)getEndpoint:(NSString*)uri with:(void (^)(NSArray *, NSError *))block
{
    JSONRequest *req = [[JSONRequest alloc ] initWithBase:base];
    [req performRequestWithUri:uri method:@"GET" body:nil completion:block];
}

+ (void)getAllLiquors:(void (^)(NSArray *, NSError *))completionBlock
{
    [self getEndpoint:@"liquors" with:completionBlock];
}

+ (void)getAllMixers:(void (^)(NSArray *, NSError *))completionBlock
{
    [self getEndpoint:@"mixers" with:completionBlock];
}
+ (void)postToThink:(NSArray*)options
     withCompletion:(void (^)(NSArray *, NSError *))completionBlock
{
    JSONRequest *req = [[JSONRequest alloc ] initWithBase:base];
    [req performRequestWithUri:@"think"
                        method:@"POST"
                          body:(NSDictionary*)options //TODO: is this gonna work?
                    completion:completionBlock];
}

@end
