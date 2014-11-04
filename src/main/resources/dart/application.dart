import 'dart:html';
import 'package:intl/intl.dart';

void main() {
  ElementList dateElements = document.querySelectorAll('.base-article .date');

  dateElements.forEach((Element dateElement) {
    String dateString = dateElement.innerHtml.replaceFirst("\[UTC\]", "");
    DateTime date = DateTime.parse(dateString).toLocal();
    DateFormat dateFormat = new DateFormat("EEE, MMM d, ''yy");
    dateElement.innerHtml = dateFormat.format(date);
  });

  Element headerElement = document.querySelector('#app-header');
  window.onScroll.listen((Event e) {
    if (window.scrollY > 100) {
      headerElement.classes.add('small');
    } else {
      headerElement.classes.remove('small');
    }
  });
}
