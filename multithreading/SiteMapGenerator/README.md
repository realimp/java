## The Task

Write an application to generate a map of a web site using multi-threading and save it to file. Links for child pages should be written with indents by one tabulation relative to the parent.

#### Example

```
https://skillbox.ru/
       https://skillbox.ru/media/
              https://skillbox.ru/media/management/
                     https://skillbox.ru/media/management/kak_rat_podkhod/
```

Saved map should contain links to pages in the same domain only.