package com.example.cueflowsapp.main_screen.parcing.text_parsing.data

import com.example.cueflowsapp.R

val DynamicScreenObjectsDataLeft = listOf(
    DynamicScreenObject(
        screenName = "Video",
        title = "Video",
        titleColor = 0xFF0B183A.toInt(),
        image = R.drawable.video_header,
        firstButtons = listOf(
            ActionButton(
                text = "YouTube",
                destination = "youtube",
                backColor = 0xFFE24A59.toInt(),
                informationAbout = ""
            )
        ),
        secondSubtitle = "Video format",
        secondSubtitleColor = 0xFF00060E.toInt(),
        secondButtons = listOf(
            ActionButton(
                text = ".mp4",
                destination = "mp4",
                backColor = 0xFF38718F.toInt(),
                informationAbout = ""
            )
        ),
        secondDescription = "Development of .mov, .avi, .mkv format processing functions is in progress",
        secondDescColor = 0xFF38718F.toInt()
    ),
    DynamicScreenObject(
        screenName = "PPTX",
        title = "Presentation",
        titleColor = 0xFF1F1A3C.toInt(),
        image = R.drawable.pptx_header,
        firstSubtitle = "Editable format",
        firstSubtitleColor = 0xFF1F1B3C.toInt(),
        firstButtons = listOf(
            ActionButton(
                text = ".pptx",
                destination = "pptx",
                backColor = 0xFFFBBF00.toInt(),
                informationAbout = ""
            )
        ),
        firstDescription = "Develop functions to process .ppt and .gslides formats in the process",
        firstDescColor = 0xA6435887.toInt(),
        secondSubtitle = "Viewing format",
        secondSubtitleColor = 0xFF1F1B3C.toInt(),
        secondButtons = listOf(
            ActionButton(
                text = ".pdf",
                destination = "pptx_pdf",
                backColor = 0xFF264E65.toInt(),
                informationAbout = ""
            )
        ),
        secondDescription = "Develop functions to process .jpeg and .png format in the process",
        secondDescColor = 0xAB435887.toInt()
    ),
    DynamicScreenObject(
        screenName = "Audio",
        title = "Audio",
        titleColor = 0xFF572E12.toInt(),
        image = R.drawable.audio_header,
        firstSubtitle = "Lossy audio format",
        firstSubtitleColor = 0xFF572E12.toInt(),
        firstButtons = listOf(
            ActionButton(
                text = ".mp3",
                destination = "mp3",
                backColor = 0xFFBBB3DC.toInt(),
                informationAbout = ""
            ),
            ActionButton(
                text = ".aac",
                destination = "aac",
                backColor = 0xFFF29494.toInt(),
                informationAbout = ""
            )
        ),
        firstDescription = "Develop functions to process .amr and .opus formats in the process",
        firstDescColor = 0xA8582F13.toInt(),
        secondSubtitle = "Uncompressed audio format",
        secondSubtitleColor = 0xFF582F13.toInt(),
        secondButtons = listOf(
            ActionButton(
                text = ".wav",
                destination = "wav",
                backColor = 0xFF627B42.toInt(),
                informationAbout = ""
            ),
            ActionButton(
                text = ".aiff?",
                destination = "aiff",
                backColor = 0xFFF09D69.toInt(),
                informationAbout = ""
            )
        ),
        secondDescription = "Develop functions to process .aiff format in the process",
        secondDescColor = 0xA8582F13.toInt()
    ),
    DynamicScreenObject(
        screenName = "E-books",
        title = "E-book",
        titleColor = 0xFF371414.toInt(),
        image = R.drawable.ebook_header,
        firstSubtitle = "Universal format",
        firstSubtitleColor = 0xFF391312.toInt(),
        firstButtons = listOf(
            ActionButton(
                text = ".epub",
                destination = "epub",
                backColor = 0xFF7E7B44.toInt(),
                informationAbout = ""
            )
        ),
        firstDescription = "Develop functions to process .mobi formats in the process",
        firstDescColor = 0xAB512426.toInt(),
        secondSubtitle = "Specialized formats",
        secondSubtitleColor = 0xFF381313.toInt(),
        secondDescription = "Develop functions to process .fb2 format in the process",
        secondDescColor = 0x995B2A2E.toInt()
    ),
    DynamicScreenObject(
        screenName = "Structure",
        title = "Structure data",
        titleColor = 0xFF492941.toInt(),
        image = R.drawable.structure_header,
        firstButtons = listOf(
            ActionButton(
                text = ".json",
                destination = "json",
                backColor = 0xFF4264A4.toInt(),
                informationAbout = ""
            ),
            ActionButton(
                text = ".xml",
                destination = "xml",
                backColor = 0xFFC02962.toInt(),
                informationAbout = ""
            )
        ),
        firstDescription = "Develop functions to process .sql  formats in the process",
        firstDescColor = 0xB8492941.toInt(),
        secondSubtitle = "Document format",
        secondSubtitleColor = 0xFF00060E.toInt()
    )
)

val DynamicScreenObjectsDataRight = listOf(
    DynamicScreenObject(
        screenName = "Text Docs",
        title = "Text and Documents",
        titleColor = 0xFF00060E.toInt(),
        image = R.drawable.text_header,
        firstSubtitle = "Text format",
        firstSubtitleColor = 0xFF00060E.toInt(),
        firstButtons = listOf(
            ActionButton(
                text = ".rtf",
                destination = "rtf",
                backColor = 0xFF5B96C9.toInt(),
                informationAbout = ""
            ),
            ActionButton(
                text = ".txt",
                destination = "txt",
                backColor = 0xFF3D739C.toInt(),
                informationAbout = ""
            )
        ),
        secondSubtitle = "Document format",
        secondSubtitleColor = 0xFF00060E.toInt(),
        secondButtons = listOf(
            ActionButton(
                text = ".docx",
                destination = "docx",
                backColor = 0xFF264E65.toInt(),
                informationAbout = ""
            ),
            ActionButton(
                text = ".pdf",
                destination = "pdf",
                backColor = 0xFF42647F.toInt(),
                informationAbout = ""
            )
        ),
        secondDescription = "Development of .doc format processing functions in the process",
        secondDescColor = 0xFF687A91.toInt()
    ),
    DynamicScreenObject(
        screenName = "Image",
        title = "Image",
        titleColor = 0xFF4C281C.toInt(),
        image = R.drawable.image_header,
        firstSubtitle = "Raster image format",
        firstSubtitleColor = 0xFF4C281C.toInt(),
        firstButtons = listOf(
            ActionButton(
                text = ".png",
                destination = "png",
                backColor = 0xFF914B36.toInt(),
                informationAbout = ""
            ),
            ActionButton(
                text = ".jpg",
                destination = "jpg",
                backColor = 0xFFF8B84C.toInt(),
                informationAbout = ""
            ),
            ActionButton(
                text = ".jpeg",
                destination = "jpeg",
                backColor = 0xFFE87439.toInt(),
                informationAbout = ""
            ),
            ActionButton(
                text = ".gif?",
                destination = "gif",
                backColor = 0xFFEE7B1E.toInt(),
                informationAbout = ""
            )
        ),
        firstDescription = "Develop functions to process .psd and .raw formats in the process",
        firstDescColor = 0xFFAD8425.toInt(),
        secondSubtitle = "Vector image format",
        secondSubtitleColor = 0xFF4C281C.toInt(),
        secondButtons = listOf(
            ActionButton(
                text = ".svg",
                destination = "svg",
                backColor = 0xFFC2572F.toInt(),
                informationAbout = ""
            ),
            ActionButton(
                text = ".pdf??",
                destination = "pdf_image",
                backColor = 0xFFFFA713.toInt(),
                informationAbout = ""
            )
        )
    ),
    DynamicScreenObject(
        screenName = "Table",
        title = "Table",
        titleColor = 0xFF01184C.toInt(),
        image = R.drawable.table_header,
        firstSubtitle = "Editable format",
        firstSubtitleColor = 0xFF01184C.toInt(),
        firstButtons = listOf(
            ActionButton(
                text = ".xlsx",
                destination = "xlsx",
                backColor = 0xFF1574DE.toInt(),
                informationAbout = ""
            ),
            ActionButton(
                text = ".gsheets",
                destination = "gsheets",
                backColor = 0xFF7140BE.toInt(),
                informationAbout = ""
            )
        ),
        firstDescription = "Develop functions to process .csv and .xls formats in the process",
        firstDescColor = 0xA6435887.toInt(),
        secondSubtitle = "Viewing format",
        secondSubtitleColor = 0xFF1F1B3C.toInt(),
        secondDescription = "Develop functions to process .pdf and image formats in the process",
        secondDescColor = 0xA6435887.toInt()
    ),
    DynamicScreenObject(
        screenName = "Archive",
        title = "Archive",
        titleColor = 0xFF4C4451.toInt(),
        image = R.drawable.archive_header,
        firstSubtitle = "Universal archive format",
        firstSubtitleColor = 0xFF4C4451.toInt(),
        firstButtons = listOf(
            ActionButton(
                text = ".zip",
                destination = "zip",
                backColor = 0xFFD75F60.toInt(),
                informationAbout = ""
            ),
            ActionButton(
                text = ".rar",
                destination = "rar",
                backColor = 0xFF5B8826.toInt(),
                informationAbout = ""
            )
        ),
        firstDescription = "Develop functions to process .7z format in the process",
        firstDescColor = 0xA6435887.toInt()
    ),
    DynamicScreenObject(
        screenName = "Web page",
        title = "Web page",
        titleColor = 0xFF32067B.toInt(),
        image = R.drawable.webpage_header,
        firstSubtitle = "Universal web page format",
        firstSubtitleColor = 0xFF32067B.toInt(),
        firstButtons = listOf(
            ActionButton(
                text = ".html",
                destination = "html",
                backColor = 0xFFFC6E6C.toInt(),
                informationAbout = ""
            )
        )
    )
)