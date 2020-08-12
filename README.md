# ZNavigator
ZNavigator integrates BottomNavigationView and manages the navigation between tabs and inner tabs.
Znavigator could make your bottom navigation work like YouTube's navigation, only one instance of  each tab exists and back button leads to the last tab instance.
The library also retain the fragments views so switching between tabs never recreate the tabs.

# Adding ZNavigator to your project
Quick and easy, first add this line your build.gradle file:
```java
implementation 'com.zach.znavigator:znavigator:1.0.0'
```

# How to use 
For using ZNavigation,First, your Activity where the BottomNavigationView exists should extends NavigationActivit.
Then, in order the NavigationActivity could manage your tabs you should pass the tabs fragments as a LinkedHashMap.
```java
LinkedHashMap<Integer, Fragment> rootFragments = new LinkedHashMap<>();
        rootFragments.put(R.id.tab1, new FirstTab());
        rootFragments.put(R.id.tab2,new SecondTab());
        rootFragments.put(R.id.tab3, new ThirdTab());
```
Now pass the tabs fragments and the resource ID of the fragments container.
```java
   init(rootFragments, R.id.container);
```
To manage the switching between tabs we should add these lines to listen the BottomNavigationView events
```java
   navigationView.setOnNavigationItemSelectedListener(this);     
   navigationView.setOnNavigationItemReselectedListener(this);
```
We can know when tab switched and change the BottomNavigationView menu as we want by overriding this method:
```java
@Override
    public void tabChanged(int id) {
        navigationView.getMenu().findItem(id).setChecked(true);
    }


