//
//  DatabaseClient.h
//  DJVU
//
//  Created by William Joshua Billingham on 11/6/13.
//  Copyright (c) 2013 DJVU. All rights reserved.
//

#import <Foundation/Foundation.h>

@class Party;
@class Query; // contains stuff like geo point, maybe eventually friends

@interface DatabaseClient : NSObject

+ (void)getAllLiquors:(void (^)(NSArray *, NSError *))completionBlock;
+ (void)getAllMixers:(void (^)(NSArray *, NSError *))completionBlock;
+ (void)postToThink:(NSArray*)options withCompletion:(void (^)(NSArray *, NSError *))completionBlock;


@end
