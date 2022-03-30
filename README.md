# AND - Labo 3

## Questions

### <ins> Pour le champ remark, destiné à accueillir un texte pouvant être plus long qu’une seule ligne, quelle configuration particulière faut-il faire dans le fichier XML pour que son comportement soit correct ? Nous pensons notamment à la possibilité de faire des retours à la ligne, d’activer le correcteur orthographique et de permettre au champ de prendre la taille nécessaire.</ins>

<br>

_TODO ALEC OU QUENTIN_

<br>
<hr>

### <ins> Pour afficher la date sélectionnée via le DatePicker nous pouvons utiliser un DateFormat permettant par exemple d’afficher 12 juin 1996 à partir d’une instance de Date. Le formatage des dates peut être relativement différent en fonction des langues, outre la traduction des mois, par exemple la même date en anglais britannique serait 12th June 1996 et en anglais américain June 12, 1996. Comment peut-on gérer cela au mieux ?</ins>

<br>

_TODO ALEC OU QUENTIN_

* A) Si vous avez utilisé le DatePickerDialog1 du SDK. En cas de rotation de l’écran du
smartphone lorsque le dialogue est ouvert, une exception android.view.WindowLeaked
sera présente dans les logs, à quoi est-elle dûe ?

    * _TODO ALEC OU QUENTIN_

<br>

* B) Si vous avez utilisé le MaterialDatePicker2 de la librairie Material. Est-il possible de limiter les dates sélectionnables dans le dialogue, en particulier pour une date de naissance il est peu probable d’avoir une personne née il y a plus de 110 ans ou à une date dans le futur.
Comment pouvons-nous mettre cela en place ?

    * _TODO ALEC OU QUENTIN_

<br>
<hr>

### <ins> Lors du remplissage des champs textuels, vous pouvez constater que le bouton « suivant » présent sur le clavier virtuel permet de sauter automatiquement au prochain champ à saisir, cf. Fig. 2. Est-ce possible de spécifier son propre ordre de remplissage du questionnaire ? Arrivé sur le dernier champ, est-il possible de faire en sorte que ce bouton soit lié au bouton de validation du questionnaire ? Hint : Le champ remark, multilignes, peut provoquer des effets de bords en fonction du clavier virtuel utilisé. Vous pouvez l’échanger avec le champ e-mail pour faciliter vos recherches concernant la réponse à cette question.</ins>

<br>

_TODO ALEC OU QUENTIN_

<br>
<hr>

### <ins> Pour la prise de vue du selfie, nous souhaiterions que l’application « appareil photo » démarre directement sur la caméra frontale. L’API supporte-t’elle officiellement un tel paramètre ? Est-il tout de même possible de le réaliser, si oui comment ?</ins>

<br>

En ce qui concerne l'API, [l'intent ACTION_IMAGE_CAPTURE du MediaStore](https://developer.android.com/reference/android/provider/MediaStore#ACTION_IMAGE_CAPTURE) n'en fait pas explicitement référence. 

En cherchant sur internet, il s'avère possible d'ouvrir l'appareil grâce à des extras. Toutefois les solutions ont l'air de varier en fonction de la version de l'API android et du téléphone. 

Sur ce [site](https://localcoder.org/how-to-launch-front-camera-with-intent) divers solutions sont exposées. 

La solution 3 a trés bien fonctionnée sur Android Galaxy S21 FE, API 32.

```
Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
    putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

    putExtra("com.google.assistant.extra.USE_FRONT_CAMERA", true)
    putExtra("android.intent.extra.USE_FRONT_CAMERA", true)
    putExtra("android.intent.extras.LENS_FACING_FRONT", 1)
    putExtra("android.intent.extras.CAMERA_FACING", 1)

    // Samsung
    putExtra("camerafacing", "front")
    putExtra("previous_mode", "front")

    // Huawei
    putExtra("default_camera", "1")
    putExtra("default_mode", "com.huawei.camera2.mode.photo.PhotoMode")
}
```

Selon nous, une version moins bancale serait de créer notre propre activité utilisant la caméra, comme la solution 4:

```
Camera cam = null;
Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
for (int camNo = 0; camNo < Camera.getNumberOfCameras(); camNo++) {
    CameraInfo camInfo = new CameraInfo();
    Camera.getCameraInfo(camNo, camInfo);
    if (camInfo.facing.equals(Camera.CameraInfo.CAMERA_FACING_FRONT)) {
        cam = Camera.open(camNo);
    }
}
if (cam == null) {
   // no front-facing camera, use the first back-facing camera instead.
   // you may instead wish to inform the user of an error here...
   cam = Camera.open();
}
// ... do stuff with Camera cam ...
```

Cependant, cette solution a le désaventage d'être plus pénible à implémenter. Est-ce que ça vaut vraiment la peine juste pour ouvrir la camera frontale directement? Nous en doutons...

<br>
<hr>

### <ins> Pour les deux Spinners (nationalité et secteur d’activité), comment peut-on faire en sorte que le premier choix corresponde au choix null, affichant par exemple « Sélectionner » ? Comment peut-on gérer cette valeur pour ne pas qu’elle soit confondue avec une réponse ?</ins>

<br>

A essayer avec ça [poste](https://stackoverflow.com/questions/867518/how-to-make-an-android-spinner-with-initial-text-select-one)

<br>

