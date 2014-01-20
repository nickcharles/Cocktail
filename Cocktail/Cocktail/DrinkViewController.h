//
//  DrinkViewController.h
//  Cocktail
//
//  Created by Otto Sipe on 1/18/14.
//
//

#import <UIKit/UIKit.h>

@interface DrinkViewController : UITableViewController <UIAlertViewDelegate>

// set by sending VC
@property NSMutableArray *options;
@property NSMutableDictionary *colors;

@end
