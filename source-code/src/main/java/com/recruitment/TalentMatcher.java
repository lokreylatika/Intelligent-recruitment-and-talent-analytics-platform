package com.recruitment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TalentMatcher {

public static int calculateMatchScore(String candidateSkills,
String requiredSkills) {

if (candidateSkills == null || requiredSkills == null) {
return 0;
}

String[] candidateArray =
candidateSkills.toLowerCase().split(",");

String[] requiredArray =
requiredSkills.toLowerCase().split(",");

Set<String> candidateSet = new HashSet<>();

for (String skill : candidateArray) {
candidateSet.add(skill.trim());
}

int matchedSkills = 0;

for (String skill : requiredArray) {
if (candidateSet.contains(skill.trim())) {
matchedSkills++;
}
}

if (requiredArray.length == 0) {
return 0;
}

return (matchedSkills * 100) / requiredArray.length;
}
}