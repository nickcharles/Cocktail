//
//  JSONRequest.h
//  DJVU
//
//  Created by Otto Sipe on 11/6/13.
//  Copyright (c) 2013 DJVU. All rights reserved.
//


// Adapted from Tom Arnfeld's JSON API Wrapper
// http://forrst.com/posts/The_simplest_JSON_API_Wrapper_method_in_Objectiv-whF
#import <Foundation/Foundation.h>

@interface JSONRequest : NSObject

@property (nonatomic, strong) NSString *baseAPIendpoint;
@property (nonatomic, strong) NSString *queueName;

- (id)initWithBase:(NSString*)base;
- (void)performRequestWithUri:(NSString *)requestURI
                       method:(NSString *)method
                       body:(NSDictionary *)body
                   completion:(void (^)(id, NSError *))completion;
@end
