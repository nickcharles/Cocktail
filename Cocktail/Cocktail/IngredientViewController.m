//
//  IngredientViewController.m
//  Cocktail
//
//  Created by Otto Sipe on 1/18/14.
//
//

#import "IngredientViewController.h"
#import "DrinkViewController.h"
#import "DatabaseClient.h"

#define LIQUORS 0
#define MIXERS 1

@interface IngredientViewController ()

-(UIColor*)colorWithHexString:(NSString*)hex;

@property NSMutableArray *liquors;
@property NSMutableArray *mixers;
@property NSMutableSet *options;
@property NSMutableDictionary *colors;

@end

@implementation IngredientViewController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    // Uncomment the following line to preserve selection between presentations.
    self.clearsSelectionOnViewWillAppear = NO;
    
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    //self.navigationItem.rightBarButtonItem = self.editButtonItem;
    
    self.colors = [[NSMutableDictionary alloc] init];
    
    [DatabaseClient getAllLiquors:^(NSArray *arr, NSError *err) {
        self.liquors = [NSMutableArray arrayWithArray:arr];
        
        [self readColors:self.liquors];
        [self.tableView reloadSections:[NSIndexSet indexSetWithIndex:LIQUORS]
                      withRowAnimation:UITableViewRowAnimationFade];
    }];
    
    [DatabaseClient getAllMixers:^(NSArray *arr, NSError *err) {
        self.mixers = [NSMutableArray arrayWithArray:arr];
        [self readColors:self.mixers];
        [self.tableView reloadSections:[NSIndexSet indexSetWithIndex:MIXERS]
                      withRowAnimation:UITableViewRowAnimationFade];
    }];
    
    
    self.options = [[NSMutableSet alloc] init];
}

- (void)motionEnded:(UIEventSubtype)motion withEvent:(UIEvent *)event {
    if (event.subtype == UIEventSubtypeMotionShake) {
        [self performSegueWithIdentifier:@"ShakeSegue" sender:self];
    }
}

- (BOOL)canBecomeFirstResponder {
    return YES;
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
    [self becomeFirstResponder];
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    [self resignFirstResponder];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 2;
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section
{
    if (section == LIQUORS) {
        return NSLocalizedString(@"Liquors", nil);
    } else {
        return NSLocalizedString(@"Mixers", nil);
    }
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    if (section == LIQUORS) {
        return [self.liquors count];
    } else {
        return [self.mixers count];
    }
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"IngredientCell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
 
    NSMutableDictionary *item = nil;
    if (indexPath.section == LIQUORS) {
        item = self.liquors[indexPath.row];
    } else {
        item = self.mixers[indexPath.row];
    }
    
    NSString *type = item[@"type"];
    [cell.textLabel setText:[type capitalizedString]];
    
    UIView *selectionColor = [[UIView alloc] init];
    selectionColor.backgroundColor = self.colors[type];
    cell.selectedBackgroundView = selectionColor;
    
    cell.accessoryType = UITableViewCellAccessoryNone;
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
    cell.accessoryType = UITableViewCellAccessoryCheckmark;
    
    [self.options addObject:[cell.textLabel.text lowercaseString]];
}

- (void)tableView:(UITableView *)tableView didDeselectRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
    cell.accessoryType = UITableViewCellAccessoryNone;
    
    [self.options removeObject:[cell.textLabel.text lowercaseString]];
}

/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    }   
    else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

#pragma mark - Navigation

// In a story board-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    if ([segue.identifier isEqualToString:@"ShakeSegue"]) {
        
        DrinkViewController *drinkVC = [segue destinationViewController];
        drinkVC.colors = self.colors;
        drinkVC.options = [NSMutableArray arrayWithArray:[self.options allObjects] ];
        
    }
}

- (void)readColors:(NSMutableArray*)dict
{
    for (NSDictionary *item in dict) {
        UIColor *color = [self colorWithHexString:item[@"color"]];
        [self.colors setObject:color forKey:item[@"type"]];
    }
}

// thanks to WrightsCS http://stackoverflow.com/questions/6207329
-(UIColor*)colorWithHexString:(NSString*)hex
{
    NSString *cString = [[hex stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]] uppercaseString];
    
    // String should be 6 or 8 characters
    if ([cString length] < 6) return [UIColor grayColor];
    
    // strip 0X if it appears
    if ([cString hasPrefix:@"0X"]) cString = [cString substringFromIndex:2];
    
    if ([cString length] != 6) return  [UIColor grayColor];
    
    // Separate into r, g, b substrings
    NSRange range;
    range.location = 0;
    range.length = 2;
    NSString *rString = [cString substringWithRange:range];
    
    range.location = 2;
    NSString *gString = [cString substringWithRange:range];
    
    range.location = 4;
    NSString *bString = [cString substringWithRange:range];
    
    // Scan values
    unsigned int r, g, b;
    [[NSScanner scannerWithString:rString] scanHexInt:&r];
    [[NSScanner scannerWithString:gString] scanHexInt:&g];
    [[NSScanner scannerWithString:bString] scanHexInt:&b];
    
    return [UIColor colorWithRed:((float) r / 255.0f)
                           green:((float) g / 255.0f)
                            blue:((float) b / 255.0f)
                           alpha:1.0f];
}


@end
