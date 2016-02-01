# ruli [ ![Download](https://api.bintray.com/packages/mediavrog/maven/ruli/images/download.svg) ](https://bintray.com/mediavrog/maven/ruli/_latestVersion)

> A tiny rule engine. 

Ruli is the tiny rule engine behind the Android library
 [Integrated Rating Request](https://github.com/mediavrog/integrated-rating-request) - a polite way to ask for ratings.

## What is Ruli?

Ruli is a tiny rule engine for the evaluation of simple rules. If consists of a couple of classes to describe
 and evaluate rules and sets of rules:

- `Rule`: Abstract rule with `evaluate` interface to provide arbitrary results.
- `RuleSet`: Evaluate groups of rules with logical AND or OR. Implements the `Rule` interface to allow nesting.
- `RuleEngine`: A slightly enhanced `RuleSet`, which saves the last result and can notify a listener upon result availability.
- `Value`: Boxed value to support dynamic generation e.g. by querying a persistence layer.
 Use `Value.as` shorthand to box a simple variable.
- `SimpleRule`: A simple left-right hand comparison. Takes `Value`s as arguments and a `SimpleRule.Comparator`.
- `SimpleRule.Comparator`: An enum for common comparison types: 
  - `EQ` - equal
  - `NEQ` - not equal
  - `GT` - greater than
  - `GT_EQ` - greater than or equal
  - `LT` - lesser than
  - `LT_EQ` - lesser than or equal
  
Ruli promotes immutability to support thread-safety. It doesn't make assumptions about your system, so use Threads
when necessary if you expect your custom rules to take long time to evaluate.

> Ruli is open to PR's. Please add tests for any code provided. Thanks!

## Examples

### OR RuleSet

```java
SimpleRule trueRule1 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, true);
SimpleRule trueRule2 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, true);
SimpleRule falseRule1 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, false);

RuleSet set = new RuleSet.Builder(RuleSet.Mode.OR)
        .addRule(falseRule1)
        .addRule(trueRule1)
        .addRule(trueRule2)
        .build();

assertTrue(set.evaluate());

```

### AND RuleSet  

```java
SimpleRule trueRule1 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, true);
SimpleRule trueRule2 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, true);
SimpleRule falseRule1 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, false);

RuleSet set = new RuleSet.Builder(RuleSet.Mode.AND)
        .addRule(trueRule1)
        .addRule(falseRule1)
        .addRule(trueRule2)
        .build();

assertFalse(set.evaluate());
```

### RuleSet nesting

```java
SimpleRule trueRule1 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, true);
SimpleRule trueRule2 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, true);
SimpleRule falseRule1 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, false);

RuleSet trueSet = new RuleSet.Builder(RuleSet.Mode.OR)
        .addRule(falseRule1)
        .addRule(trueRule1)
        .addRule(trueRule2)
        .build();

SimpleRule falseRule2 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, false);

RuleSet set = new RuleSet.Builder(RuleSet.Mode.AND)
        .addRule(trueSet)
        .addRule(falseRule2)
        .build();

assertFalse(set.evaluate());
```

### Custom rules

```java

// fake data setup
class WeatherApi {
    public float getTemp() {
        return 30.4f;
    }
}

Integer iceEatenToday = 2;

final Calendar cal = Calendar.getInstance();
cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);

// the rules
RuleSet eatingIceTime = new RuleSet.Builder()
        // simple int comparison
        .addRule(new SimpleRule<Integer>(iceEatenToday, SimpleRule.Comparator.LT, 5))
        // float compared with return value of other object
        .addRule(new SimpleRule<Float>(new Value<Float>() {
            @Override
            public Float get() {
                return new WeatherApi().getTemp();
            }
        }, SimpleRule.Comparator.GT_EQ, 30f))
        // custom evaluation logic
        .addRule(new Rule() {
            @Override
            public boolean evaluate() {
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
            }
        })
        .build();

assertTrue("Eh? But I'm still hungry!", eatingIceTime.evaluate()); // *yum* moar!
```