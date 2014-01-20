//
//  DrinkViewController.m
//  Cocktail
//
//  Created by Otto Sipe on 1/18/14.
//
//

#import "DrinkViewController.h"
#import "DatabaseClient.h"

@interface DrinkViewController ()

@property NSMutableArray *drinks;
@property NSDictionary *currentDrink;
@property NSArray *currentIngred;
@property NSInteger totalRatio;
@property NSInteger drinkIndex;

@end

@implementation DrinkViewController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
        _totalRatio = 0;
        _drinkIndex = 0;
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];

    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
 
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
    
    [self getDrinkSuggestions];
    
    UILabel *label = [[UILabel alloc] init];
    label.text = @"1/10";
    [self.navigationController.view addSubview:label];
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

- (void)motionEnded:(UIEventSubtype)motion withEvent:(UIEvent *)event {
    if (event.subtype == UIEventSubtypeMotionShake) {
        [self setNextDrink];
    }
}
- (IBAction)goToNextDrink:(id)sender {
    [self setNextDrink];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)getDrinkSuggestions
{
    
    UIActivityIndicatorView *activityIndicator = [[UIActivityIndicatorView alloc]initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
    activityIndicator.center = CGPointMake(self.view.frame.size.width / 2.0, self.view.frame.size.height / 2.0 - 40);
    [self.view addSubview: activityIndicator];
    
    [activityIndicator startAnimating];
    
    [DatabaseClient postToThink:self.options
                 withCompletion:^(NSArray *arr, NSError *err) {
                     
                     [activityIndicator stopAnimating];
                     
                     if (arr.count != 0) {
                         
                         self.drinks = [NSMutableArray arrayWithArray:arr];
                         [self setNextDrink];
                         NSLog(@"%@", arr);
                     } else {
                         // Create a new alert object and set initial values.
                         UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"No Drinks"
                                                                         message:@"Sorry, we didn't find any drinks you can make!"
                                                                        delegate:self
                                                               cancelButtonTitle:@"Ahh, ok."
                                                               otherButtonTitles:nil];
                         // Display the alert to the user
                         [alert show];
                     }
                 }];
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    // go back to home
    [self.navigationController popViewControllerAnimated:YES];
}

- (void)setNextDrink
{
    
    if(!self.drinks.count) {
        NSLog(@"ERROR: No Drinks Found");
        return;
    }
    
    if (self.drinkIndex >= self.drinks.count) {
        self.drinkIndex = 0;
    }
    
    self.currentDrink = self.drinks[self.drinkIndex++];
    self.currentIngred = self.currentDrink[@"ingredients"];
    
    self.totalRatio = 0;
    for (NSDictionary *ingredient in self.currentIngred) {
        self.totalRatio += [ingredient[@"ratio"] integerValue];
    }
    
    // set title
    UINavigationController *navCon  = (UINavigationController*) [self.navigationController.viewControllers objectAtIndex:1];
    navCon.navigationItem.title = self.currentDrink[@"name"];
    
    // set which drink is being viewed
    UIBarButtonItem *button = navCon.navigationItem.rightBarButtonItem;
    [button setTitle:[NSString stringWithFormat:@"%ld/%ld", self.drinkIndex, self.drinks.count]];
    
    [self.tableView reloadData];
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [self.currentIngred count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"DrinkCell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
    
    
    NSDictionary *ingred = self.currentIngred[indexPath.row];
    NSString *type = ingred[@"name"];
    NSString *name = [type capitalizedString];
    
    NSLog(@"%@", ingred);
    
    UIFont *myFont = [ UIFont fontWithName:@"Helvetica" size:25.0 ];
    cell.textLabel.font = myFont;
    
    if ([ingred[@"have"] integerValue] != 0) {
        [cell.textLabel setText:name];
    } else {
        [cell.textLabel setText:[NSString stringWithFormat:@"%@ (don't have)", name]];
        /*NSDictionary* attributes = @{
                                     NSStrikethroughStyleAttributeName: [NSNumber numberWithInt:NSUnderlineStyleSingle]
                                     };
        
        NSAttributedString* attrText = [[NSAttributedString alloc] initWithString:@"My Text" attributes:attributes];
        cell.textLabel.attributedText = attrText;*/
    }
    
    cell.backgroundColor = self.colors[type];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    
    /*UISwipeGestureRecognizer* gestureR;
    gestureR = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(setNextDrink)];
    gestureR.direction = UISwipeGestureRecognizerDirectionLeft;
    [self.view addGestureRecognizer:gestureR];*/
    
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSInteger height = self.view.bounds.size.height
        - self.navigationController.navigationBar.frame.size.height
        - [UIApplication sharedApplication].statusBarFrame.size.height + 1;
    NSDictionary *ingred = self.currentIngred[indexPath.row];
    NSInteger ratio = [ingred[@"ratio"] integerValue];
    
    return height*ratio/self.totalRatio;
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

/*
#pragma mark - Navigation

// In a story board-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}

 */

@end
