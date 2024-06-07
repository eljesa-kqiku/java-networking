**Detyra**


1. Të shkruhet aplikacioni për komunikimin klient-server (TCP) ku të dhënat mes
   aplikacioneve shkëmbehen të enkriptuara me algortimin AES. Aplikacioni duhet të ketë
   GUI për përdoruesin (klientin) dhe serveri duhet të pranojë njëkohësisht disa klientë. (2.5
   pikë)


2. Të shkruhet chat aplikacioni i cili mundëson komunikimin e një më një të përdoruesve. Të
   përdoret java sockets. Serveri duhet të mundësoj shumë lidhje një më një të përdoruesve.
   Përdoruesi kur hyn në chat aplikacion duhet të shoh të gjithë përdoruesit që janë lidhur
   në server dhe pastaj nga ajo listë mund të zgjedh me cilin do të komunikojë. Nëse
   përdoruesi me të cilin dëshiron të komunikojë është i zënë në një chat sesion tjetër
   atëherë duhet të shfaqet mesazhi “<username i përdoruesit> është i zënë” poashtu duhet
   te përdoruesi i zënë të shfaqet ftesa. Nëse përdoruesi nuk është i zënë e pranon ftesën
   dhe vazhdon komunikimin në chat. (2.5 pikë)


3. Duke përdorë RMI të shkruhet chat aplikacioni që mundëson komunikimin e një me një
   të përdoruesve. Serveri duhet të mundësoj shumë lidhje një më një të përdoruesve.
   Përdoruesi kur hyn në chat aplikacion duhet të shoh të gjithë përdoruesit që janë lidhur
   në server dhe pastaj nga ajo listë mund të zgjedh me cilin do të komunikojë. Nëse
   përdoruesi me të cilin dëshiron të komunikojë është i zënë në një chat sesion tjetër
   atëherë duhet të shfaqet mesazhi “<username i përdoruesit> është i zënë” poashtu duhet
   te përdoruesi i zënë të shfaqet ftesa. Nëse përdoruesi nuk është i zënë e pranon ftesën
   dhe vazhdon komunikimin në chat. (2 pikë)


4. Sistemi për rezervimin e dhomave në hotel
   Krijoni një aplikacion që do të jetë një sistem rezervimi i dhomave duke përdorur RMI.
   Mund të supozojmë që të gjithë mund të rezervojnë dhoma për një periudhë të caktuar
   kohore. (10 pikë)
   Ka 5 lloje dhomash me çmime të ndryshme.

    10 dhoma të tipit 0 të cilat janë dhoma teke që kushtojnë 55 euro nata
    20 dhoma të tipit 1 të cilat janë dhoma dyshe që kushtojnë 75 euro nata
    5 dhoma të tipit 2 të cilat janë dhoma dyshe që kushtojnë 80 euro nata
    3 dhoma të tipit 3 të cilat janë dhoma treshe që kushtojnë 150 euro nata
    2 dhoma të tipit 4 të cilat janë katër dhoma që kushtojnë 230 euro nata

    Për të implementuar këtë sistem duhet të shkruani një server (ServeriHotelit), një klient
    (Klienti), një ndërfaqe (remote) Dhomat dhe implementimin e ndërfaqes në distancë
    (remote distance) DhomatImpl.
    Klienti duhet që të marrë informata nga ServeriHotelit për tipet e dhomave të lira dhe
    çmimet përkatëse, p.sh.

    x0 dhoma të tipit 0 ofrohen për 55 euro nata
    x1 dhoma të tipit 1 ofrohen për 75 euro për natë
    x2 dhoma të tipit 2 ofrohen për 80 euro për natë
    x3 dhomat e tipit 3 ofrohen për 150 euro nata
    x4 dhomat e tipit 4 ofrohen për 230 euro nata

    ku x0, ... , x4 është numri aktual i dhomave në disponim për secilin tip të dhomave.
    Klienti bën rezervimin dhe pastaj të dhënat përditësohen dhe kur klienti tjetër të kërkoj
    informata për dhomat e lira, duhet që të shfaqen të dhënat e përditësuara.
    Poashtu duhet që t’i mundësohet klientit që të bëjë edhe ç’regjistrimin, ku pastaj të
    dhënat përditësohen (pra dhoma ose dhomat lirohen).


5. Krijoni një aplikacion (Spark, Java) që bën numërimin e fjalëve nga një tekst dokument
   input.txt. (3 pikë)